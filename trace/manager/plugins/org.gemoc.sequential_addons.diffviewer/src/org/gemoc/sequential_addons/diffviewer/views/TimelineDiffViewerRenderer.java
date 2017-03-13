package org.gemoc.sequential_addons.diffviewer.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gemoc.sequential_addons.diffviewer.logic.Diff;
import org.gemoc.sequential_addons.diffviewer.logic.Diff.DiffKind;
import org.gemoc.sequential_addons.diffviewer.logic.DiffComputer;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

public class TimelineDiffViewerRenderer extends Pane {

	private static final int H_MARGIN = 8;
	private static final int V_MARGIN = 2;
	private static final int WIDTH = 24;
	private static final int UNIT = WIDTH + H_MARGIN * 2;

	private static final Insets HALF_MARGIN_INSETS = new Insets(V_MARGIN, H_MARGIN / 2, V_MARGIN, H_MARGIN / 2);
	private static final Insets MARGIN_INSETS = new Insets(V_MARGIN, H_MARGIN, V_MARGIN, H_MARGIN);

	private static final Paint LINE_PAINT = new Color(Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(),
			Color.LIGHTGRAY.getBlue(), 0.5);

	private static final Background LINE_BACKGROUND = new Background(new BackgroundFill(LINE_PAINT, null, null));
	private static final Background HEADER_BACKGROUND = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));
	private static final Background WHITE_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, null, null));
	private static final Background TRANSPARENT_BACKGROUND = new Background(
			new BackgroundFill(Color.TRANSPARENT, null, null));

	private static final Font STATE_FONT = Font.font("Arial", FontWeight.BOLD, 9);
	private static final Font VALUE_FONT = Font.font("Arial", FontWeight.BOLD, 11);
	private static final Font GROUP_FONT = Font.font("Arial", FontWeight.BOLD, 12);

	private final VBox rootVBox = new VBox();
	private final HBox line1 = new HBox();
	private final HBox line2 = new HBox();
	private final VBox eqLines = new VBox();
	private final VBox eqBox = new VBox();
	private final VBox substLines = new VBox();
	private final VBox substBox = new VBox();
	private final VBox inLines = new VBox();
	private final VBox inBox = new VBox();
	private final VBox delLines = new VBox();
	private final VBox delBox = new VBox();
	private final ScrollBar scrollBar = new ScrollBar();

	private final IntegerProperty nbDisplayableStates;
	private final IntegerProperty statesRange;
	private final IntegerProperty nbStates;

	private int currentState = 0;

	final Map<HBox, List<List<DiffKind>>> lineToSegments = new HashMap<>();
	final Map<HBox, List<String>> segmentToDescription = new HashMap<>();
	final Map<List<Value<?>>, String> dimensionDescriptions = new HashMap<>();

	private ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor1 = null;
	private ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor2 = null;

	private final List<List<Value<?>>> values1 = new ArrayList<>();
	private final List<List<Value<?>>> values2 = new ArrayList<>();

	private final List<State<?,?>> states1 = new ArrayList<>();
	private final List<State<?,?>> states2 = new ArrayList<>();

	private DiffComputer diffComputer;

	public TimelineDiffViewerRenderer() {

		nbStates = new SimpleIntegerProperty();
		statesRange = new SimpleIntegerProperty();
		nbDisplayableStates = new SimpleIntegerProperty();
		nbDisplayableStates.bind(widthProperty().divide(UNIT));
		statesRange.bind(nbStates.subtract(nbDisplayableStates));

		nbDisplayableStates.addListener((v, o, n) -> {
			refresh();
		});

		setupBox(eqBox, "Toggle identical traces", eqLines);
		setupBox(substBox, "Toggle similar traces", substLines);
		setupBox(inBox, "Toggle inserted traces", inLines);
		setupBox(delBox, "Toggle deleted traces", delLines);

		ScrollPane scrollPane = new ScrollPane(rootVBox);
		scrollPane.minWidthProperty().bind(widthProperty());
		scrollPane.maxWidthProperty().bind(widthProperty());
		scrollPane.prefWidthProperty().bind(widthProperty());
		scrollPane.setFitToWidth(true);
		scrollPane.setBorder(Border.EMPTY);

		VBox headerPane = new VBox();
		headerPane.minWidthProperty().bind(widthProperty());
		headerPane.maxWidthProperty().bind(widthProperty());
		headerPane.setBackground(HEADER_BACKGROUND);

		scrollPane.translateYProperty().bind(headerPane.heightProperty());
		scrollPane.maxHeightProperty().bind(heightProperty().subtract(headerPane.heightProperty()));

		getChildren().add(headerPane);
		getChildren().add(scrollPane);
		minHeightProperty().bind(headerPane.heightProperty().add(scrollPane.heightProperty()));
		prefHeightProperty().bind(headerPane.heightProperty().add(scrollPane.heightProperty()));
		maxHeightProperty().bind(headerPane.heightProperty().add(scrollPane.heightProperty()));

		scrollBar.setVisibleAmount(1);
		scrollBar.setBlockIncrement(10);
		scrollBar.setMin(0);
		scrollBar.disableProperty().bind(statesRange.lessThanOrEqualTo(0));
		scrollBar.maxProperty().bind(statesRange);
		scrollBar.valueProperty().addListener((v, o, n) -> {
			if (o.intValue() != n.intValue() && n.intValue() != currentState) {
				currentState = n.intValue();
				refresh();
			}
		});

		headerPane.getChildren().add(scrollBar);
		headerPane.getChildren().add(line1);
		headerPane.getChildren().add(line2);

		setBackground(WHITE_BACKGROUND);
		scrollPane.setBackground(WHITE_BACKGROUND);
		rootVBox.setBackground(WHITE_BACKGROUND);
	}

	private void setupBox(VBox box, String labelString, VBox content) {
		final HBox boxLabel = new HBox();
		final Polygon arrow = new Polygon(2.5, 10, 10, 5, 2.5, 0);
		final Label label = new Label(labelString);
		boxLabel.setBackground(HEADER_BACKGROUND);
		label.setFont(GROUP_FONT);
		HBox.setMargin(arrow, HALF_MARGIN_INSETS);
		boxLabel.setAlignment(Pos.CENTER_LEFT);
		boxLabel.getChildren().addAll(arrow, label);
		boxLabel.setCursor(Cursor.HAND);
		box.getChildren().add(boxLabel);
		boxLabel.setOnMouseClicked(e -> {
			if (box.getChildren().size() > 1) {
				box.getChildren().remove(content);
				arrow.setRotate(0);
			} else {
				box.getChildren().add(content);
				arrow.setRotate(90);
			}
		});
	}

	private String computeStateLabel(int stateNumber) {
		if (stateNumber > 999) {
			return (stateNumber / 1000) + "k" + ((stateNumber % 1000) / 10);
		} else {
			return "" + stateNumber;
		}
	}

	private void addState(State<?,?> state, HBox line, Color color, int stateIndex, String stateDescription) {
		final Rectangle rectangle = new Rectangle(WIDTH, WIDTH, color);
		rectangle.setArcHeight(WIDTH);
		rectangle.setArcWidth(WIDTH);
		rectangle.setUserData(state);
		Label text = new Label(computeStateLabel(stateIndex));
		text.setTextOverrun(OverrunStyle.ELLIPSIS);
		text.setAlignment(Pos.CENTER);
		text.setMouseTransparent(true);
		text.setTextFill(Color.WHITE);
		text.setFont(STATE_FONT);
		text.setMaxWidth(WIDTH);
		final Tooltip tooltip = new Tooltip(stateDescription);
		Tooltip.install(rectangle, tooltip);
		StackPane layout = new StackPane();
		StackPane.setMargin(rectangle, MARGIN_INSETS);
		layout.getChildren().addAll(rectangle, text);
		line.getChildren().add(layout);
	}

	private void addBlankState(HBox line) {
		final Rectangle rectangle = new Rectangle(WIDTH, WIDTH, Color.TRANSPARENT);
		HBox.setMargin(rectangle, MARGIN_INSETS);
		line.getChildren().add(rectangle);
	}

	private void addValue(Value<?> value, HBox line, String description, boolean newValue, DiffKind diffKind) {
		final List<List<DiffKind>> segments = lineToSegments.get(line);
		List<DiffKind> segment;
		boolean addDescription = false;
		if (segments.isEmpty()) {
			segment = new ArrayList<>();
			addDescription = true;
			segments.add(segment);
		} else {
			if (newValue) {
				segment = new ArrayList<>();
				addDescription = true;
				segments.add(segment);
			} else {
				segment = segments.get(segments.size() - 1);
				if (segment == null) {
					segment = new ArrayList<>();
					addDescription = true;
					segments.add(segment);
				}
			}

		}
		if (addDescription) {
			List<String> descriptions = segmentToDescription.get(line);
			if (descriptions == null) {
				descriptions = new ArrayList<>();
				segmentToDescription.put(line, descriptions);
			}
			descriptions.add(description);
		}
		segment.add(diffKind);
	}
	
	private void addValue(Value<?> value, HBox line, String description, boolean newValue) {
		addValue(value, line, description, newValue, DiffKind.EQ);
	}

	private void addDelayedValue(HBox line, String description) {
		final List<List<DiffKind>> segments = lineToSegments.get(line);
		final List<DiffKind> segment;
		boolean addDescription = false;
		if (segments.isEmpty()) {
			segment = new ArrayList<>();
			addDescription = true;
			segments.add(segment);
		} else {
			segment = segments.get(segments.size() - 1);
		}
		if (addDescription) {
			List<String> descriptions = segmentToDescription.get(line);
			if (descriptions == null) {
				descriptions = new ArrayList<>();
				segmentToDescription.put(line, descriptions);
			}
			descriptions.add(description);
		}
		segment.add(DiffKind.DEL);
	}

	private void addBlankValue(HBox line) {
		lineToSegments.get(line).add(null);
	}

	private List<Value<?>> normalizeValueTrace(List<Value<?>> trace, int start, int end, ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor) {
		final List<Value<?>> result = new ArrayList<>();
		for (Value<?> w : trace) {
			final int firstStateIndex = extractor.getValueFirstStateIndex(w);
			final int lastStateIndex = extractor.getValueLastStateIndex(w);
			while (result.size() < firstStateIndex - start) {
				result.add(null);
			}
			for (int i = Math.max(firstStateIndex, start); i <= Math.min(lastStateIndex, end); i++) {
				result.add(w);
			}
		}
		return result;
	}

	public void loadTraces(final ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor1,
			final ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor2) {
		loadTraces(extractor1, extractor2, 0, 0, extractor1.getStatesTraceLength() - 1, extractor2.getStatesTraceLength() - 1);
	}
	
	public void loadTraces(final ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor1,
			final ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor2, int s1, int s2, int e1, int e2) {
		this.extractor1 = extractor1;
		this.extractor2 = extractor2;
		values1.clear();
		values2.clear();
		states1.clear();
		states2.clear();
		dimensionDescriptions.clear();

		states1.addAll(extractor1.getStates(s1, e1));
		for (Dimension<?> dimension : this.extractor1.getDimensions()) {
			final List<Value<?>> values = extractor1.getValuesForStates(dimension, s1, e1);
			final List<Value<?>> normalizedValues = normalizeValueTrace(values, s1, e1, extractor1);
			while (normalizedValues.size() < e1 - s1 + 1) {
				normalizedValues.add(null);
			}
			values1.add(normalizedValues);
			dimensionDescriptions.put(normalizedValues, extractor1.getDimensionLabel(dimension));
		}

		states2.addAll(extractor2.getStates(s2, e2));
		for (Dimension<?> dimension : this.extractor2.getDimensions()) {
			final List<Value<?>> values = extractor2.getValuesForStates(dimension, s2, e2);
			final List<Value<?>> normalizedValues = normalizeValueTrace(values, s2, e2, extractor2);
			while (normalizedValues.size() < e2 - s2 + 1) {
				normalizedValues.add(null);
			}
			values2.add(normalizedValues);
			dimensionDescriptions.put(normalizedValues, extractor1.getDimensionLabel(dimension));
		}

		diffComputer = new DiffComputer();
		diffComputer.loadTraces(values1, values2);
		nbStates.set(diffComputer.getDiffs().size());
		currentState = 0;
		scrollBar.setValue(0);
		
		refresh();
	}

	private boolean isNewValue(int idx, List<Value<?>> list) {
		return idx == 0 || (idx < list.size() && idx > 0 && list.get(idx - 1) != list.get(idx));
	}

	private void fillGap(HBox line, List<Value<?>> trace, int idx, String description) {
		if (idx > 0 && idx < trace.size()) {
			if (trace.get(idx - 1) != null || (idx < trace.size() - 1 && trace.get(idx + 1) != null)) {
				addDelayedValue(line, description);
			} else {
				addBlankValue(line);
			}
		} else {
			addBlankValue(line);
		}
	}

	public void refresh() {
		if (diffComputer != null) {
			line1.getChildren().clear();
			line2.getChildren().clear();
			eqLines.getChildren().clear();
			substLines.getChildren().clear();
			inLines.getChildren().clear();
			delLines.getChildren().clear();
			lineToSegments.clear();
			rootVBox.getChildren().clear();

			final List<Pair<List<Value<?>>, List<Value<?>>>> eqGroup = diffComputer.getEqGroup();
			final List<Pair<List<Value<?>>, List<Value<?>>>> substGroup = diffComputer.getSubstGroup();
			final List<List<Value<?>>> inGroup = diffComputer.getInGroup();
			final List<List<Value<?>>> delGroup = diffComputer.getDelGroup();

			if (!eqGroup.isEmpty()) {
				rootVBox.getChildren().add(eqBox);
			}

			if (!substGroup.isEmpty()) {
				rootVBox.getChildren().add(substBox);
			}

			if (!inGroup.isEmpty()) {
				rootVBox.getChildren().add(inBox);
			}

			if (!delGroup.isEmpty()) {
				rootVBox.getChildren().add(delBox);
			}

			final Map<List<Value<?>>, HBox> traceToLine = new HashMap<>();

			int c = 0;

			for (Pair<List<Value<?>>, List<Value<?>>> e : eqGroup) {
				final VBox pairBox = new VBox();
				final HBox trace1Box = new HBox();
				final HBox trace2Box = new HBox();
				traceToLine.put(e.getKey(), trace1Box);
				traceToLine.put(e.getValue(), trace2Box);
				lineToSegments.put(trace1Box, new ArrayList<>());
				lineToSegments.put(trace2Box, new ArrayList<>());
				Label l1 = new Label(dimensionDescriptions.get(e.getKey()));
				Label l2 = new Label(dimensionDescriptions.get(e.getValue()));
				VBox.setMargin(l1, HALF_MARGIN_INSETS);
				VBox.setMargin(l2, HALF_MARGIN_INSETS);
				l1.setFont(VALUE_FONT);
				l2.setFont(VALUE_FONT);
				pairBox.getChildren().addAll(l1, trace1Box, l2, trace2Box);
				eqLines.getChildren().add(pairBox);
				pairBox.setBackground(c % 2 == 0 ? LINE_BACKGROUND : WHITE_BACKGROUND);
				trace1Box.setBackground(TRANSPARENT_BACKGROUND);
				trace2Box.setBackground(TRANSPARENT_BACKGROUND);
				c++;
			}

			for (Pair<List<Value<?>>, List<Value<?>>> e : substGroup) {
				final VBox pairBox = new VBox();
				final HBox trace1Box = new HBox();
				final HBox trace2Box = new HBox();
				traceToLine.put(e.getKey(), trace1Box);
				traceToLine.put(e.getValue(), trace2Box);
				lineToSegments.put(trace1Box, new ArrayList<>());
				lineToSegments.put(trace2Box, new ArrayList<>());
				Label l1 = new Label(dimensionDescriptions.get(e.getKey()));
				Label l2 = new Label(dimensionDescriptions.get(e.getValue()));
				VBox.setMargin(l1, HALF_MARGIN_INSETS);
				VBox.setMargin(l2, HALF_MARGIN_INSETS);
				l1.setFont(VALUE_FONT);
				l2.setFont(VALUE_FONT);
				pairBox.getChildren().addAll(l1, trace1Box, l2, trace2Box);
				substLines.getChildren().add(pairBox);
				pairBox.setBackground(c % 2 == 0 ? LINE_BACKGROUND : WHITE_BACKGROUND);
				trace1Box.setBackground(TRANSPARENT_BACKGROUND);
				trace2Box.setBackground(TRANSPARENT_BACKGROUND);
				c++;
			}

			for (List<Value<?>> in : inGroup) {
				final VBox inVBox = new VBox();
				final HBox traceBox = new HBox();
				traceToLine.put(in, traceBox);
				lineToSegments.put(traceBox, new ArrayList<>());
				Label l = new Label(dimensionDescriptions.get(in));
				VBox.setMargin(l, HALF_MARGIN_INSETS);
				l.setFont(VALUE_FONT);
				inVBox.getChildren().addAll(l, traceBox);
				inLines.getChildren().add(inVBox);
				traceBox.setBackground(c % 2 == 0 ? LINE_BACKGROUND : WHITE_BACKGROUND);
				c++;
			}

			for (List<Value<?>> del : delGroup) {
				final VBox delVBox = new VBox();
				final HBox traceBox = new HBox();
				traceToLine.put(del, traceBox);
				lineToSegments.put(traceBox, new ArrayList<>());
				Label l = new Label(dimensionDescriptions.get(del));
				VBox.setMargin(l, HALF_MARGIN_INSETS);
				l.setFont(VALUE_FONT);
				delVBox.getChildren().addAll(l, traceBox);
				delLines.getChildren().add(delVBox);
				traceBox.setBackground(c % 2 == 0 ? LINE_BACKGROUND : WHITE_BACKGROUND);
				c++;
			}

			final List<Diff> diffs = diffComputer.getDiffs();
			for (Diff diff : diffs.subList(currentState,
					Math.min(currentState + nbDisplayableStates.intValue(), diffs.size()))) {
				int i = diff.idx1;
				int j = diff.idx2;
				switch (diff.kind) {
				case EQ: {
					final State<?,?> state1 = states1.get(i);
					final State<?,?> state2 = states2.get(j);
					addState(state1, line1, Color.SLATEBLUE, extractor1.getStateIndex(state1), extractor1.getStateDescription(state1));
					addState(state2, line2, Color.SLATEBLUE, extractor2.getStateIndex(state2), extractor2.getStateDescription(state2));
					for (Pair<List<Value<?>>, List<Value<?>>> e : eqGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						final Value<?> v1 = t1.get(i);
						final Value<?> v2 = t2.get(j);
						String d1 = extractor1.getValueDescription(v1);
						String d2 = extractor2.getValueDescription(v2);
						addValue(v1, traceToLine.get(t1), d1, isNewValue(i, t1));
						addValue(v2, traceToLine.get(t2), d2, isNewValue(j, t2));
					}
					for (Pair<List<Value<?>>, List<Value<?>>> e : substGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						final Value<?> v1 = t1.get(i);
						final Value<?> v2 = t2.get(j);
						String d1 = extractor1.getValueDescription(v1);
						String d2 = extractor2.getValueDescription(v2);
						addValue(v1, traceToLine.get(t1), d1, isNewValue(i, t1));
						addValue(v2, traceToLine.get(t2), d2, isNewValue(j, t2));
					}
					for (List<Value<?>> del : delGroup) {
						String d = extractor1.getValueDescription(del.get(i));
						addValue(del.get(i), traceToLine.get(del), d, isNewValue(i, del));
					}
					for (List<Value<?>> in : inGroup) {
						String d = extractor2.getValueDescription(in.get(j));
						addValue(in.get(j), traceToLine.get(in), d, isNewValue(j, in));
					}
					break;
				}
				case SUBST: {
					final State<?,?> state1 = states1.get(i);
					final State<?,?> state2 = states2.get(j);
					addState(state1, line1, Color.TOMATO, extractor1.getStateIndex(state1), extractor1.getStateDescription(state1));
					addState(state2, line2, Color.TOMATO, extractor2.getStateIndex(state2), extractor2.getStateDescription(state2));
					for (Pair<List<Value<?>>, List<Value<?>>> e : eqGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						addValue(t1.get(i), traceToLine.get(t1), d1, isNewValue(i, t1));
						addValue(t2.get(j), traceToLine.get(t2), d2, isNewValue(j, t2));
					}
					for (Pair<List<Value<?>>, List<Value<?>>> e : substGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						if (diffComputer.compareEObjects(t1.get(i), t2.get(j))) {
							addValue(t1.get(i), traceToLine.get(t1), d1, isNewValue(i, t1));
							addValue(t2.get(j), traceToLine.get(t2), d2, isNewValue(j, t2));
						} else {
							addValue(t1.get(i), traceToLine.get(t1), d1, isNewValue(i, t1), DiffKind.SUBST);
							addValue(t2.get(j), traceToLine.get(t2), d2, isNewValue(j, t2), DiffKind.SUBST);
						}
					}
					for (List<Value<?>> del : delGroup) {
						String d = extractor1.getValueDescription(del.get(i));
						addValue(del.get(i), traceToLine.get(del), d, isNewValue(i, del));
					}
					for (List<Value<?>> in : inGroup) {
						String d = extractor2.getValueDescription(in.get(j));
						addValue(in.get(j), traceToLine.get(in), d, isNewValue(j, in));
					}
					break;
				}
				case DEL: {
					final State<?,?> state1 = states1.get(i);
					addState(state1, line1, Color.BROWN, extractor1.getStateIndex(state1), extractor1.getStateDescription(state1));
					addBlankState(line2);
					for (Pair<List<Value<?>>, List<Value<?>>> e : eqGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						addValue(t1.get(i), traceToLine.get(t1), d1, isNewValue(i, t1));
						fillGap(traceToLine.get(t2), t2, j, d2);
					}
					for (Pair<List<Value<?>>, List<Value<?>>> e : substGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						addValue(t1.get(i), traceToLine.get(t1), d1, isNewValue(i, t1));
						fillGap(traceToLine.get(t2), t2, j, d2);
					}
					for (List<Value<?>> del : delGroup) {
						String d = extractor1.getValueDescription(del.get(i));
						addValue(del.get(i), traceToLine.get(del), d, isNewValue(i, del));
					}
					for (List<Value<?>> in : inGroup) {
						String d = extractor2.getValueDescription(in.get(j));
						fillGap(traceToLine.get(in), in, j, d);
					}
					break;
				}
				case IN: {
					final State<?,?> state2 = states2.get(i);
					addBlankState(line1);
					addState(state2, line2, Color.BROWN, extractor2.getStateIndex(state2), extractor2.getStateDescription(state2));
					for (Pair<List<Value<?>>, List<Value<?>>> e : eqGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						fillGap(traceToLine.get(t1), t1, i, d1);
						addValue(t2.get(j), traceToLine.get(t2), d2, isNewValue(j, t2));
					}
					for (Pair<List<Value<?>>, List<Value<?>>> e : substGroup) {
						final List<Value<?>> t1 = e.getKey();
						final List<Value<?>> t2 = e.getValue();
						String d1 = extractor1.getValueDescription(t1.get(i));
						String d2 = extractor2.getValueDescription(t2.get(j));
						fillGap(traceToLine.get(t1), t1, i, d1);
						addValue(t2.get(j), traceToLine.get(t2), d2, isNewValue(j, t2));
					}
					for (List<Value<?>> del : delGroup) {
						String d = extractor1.getValueDescription(del.get(i));
						fillGap(traceToLine.get(del), del, i, d);
					}
					for (List<Value<?>> in : inGroup) {
						String d = extractor2.getValueDescription(in.get(j));
						addValue(in.get(j), traceToLine.get(in), d, isNewValue(j, in));
					}
					break;
				}
				}
			}
			processSegments();
		}
	}

	private void processSegments() {
		for (Map.Entry<HBox, List<List<DiffKind>>> e : lineToSegments.entrySet()) {
			final HBox line = e.getKey();
			final List<String> descriptions = segmentToDescription.get(line);
			final List<Node> children = line.getChildren();
			final List<List<DiffKind>> segments = e.getValue();
			int idx = 0;
			for (List<DiffKind> segment : segments) {
				if (segment == null) {
					final Rectangle rectangle = new Rectangle(WIDTH, 8, Color.TRANSPARENT);
					HBox.setMargin(rectangle, MARGIN_INSETS);
					children.add(rectangle);
				} else {
					DiffKind[] seg = new DiffKind[segment.size()];
					for (int i = 0; i < seg.length; i++) {
						seg[i] = segment.get(i);
					}
					final String description = descriptions.get(idx);
					final ValueView view = new ValueView(description, seg);
					HBox.setMargin(view, MARGIN_INSETS);
					children.add(view);
					idx++;
				}
			}
		}
	}
}
