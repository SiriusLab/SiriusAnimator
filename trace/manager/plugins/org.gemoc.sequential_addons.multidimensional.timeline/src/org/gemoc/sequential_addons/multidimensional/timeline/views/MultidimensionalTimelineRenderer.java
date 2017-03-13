/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.gemoc.sequential_addons.multidimensional.timeline.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer;
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
import fr.inria.diverse.trace.gemoc.api.ITraceViewListener;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MultidimensionalTimelineRenderer extends Pane implements ITraceViewListener {

	private ITraceExplorer<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer;
	private ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor;

	private final IntegerProperty currentState;
	private final IntegerProperty currentStep;

	private final ScrollPane bodyScrollPane;
	private final VBox headerPane;
	private final Pane bodyPane;
	private final VBox valuesLines;

	private final DoubleProperty valueTitleWidth;
	private final DoubleProperty statesPaneHeight;
	private final BooleanProperty displayGrid;
	private final IntegerProperty nbDisplayableStates;
	private final IntegerProperty visibleStatesRange;
	private final IntegerProperty nbStates;
	private BooleanBinding displayGridBinding;
	private final Path diagonalHatching = new Path();

	private final Font statesFont = Font.font("Arial", FontWeight.BOLD, 12);
	private final Font valuesFont = Font.font("Arial", FontWeight.BOLD, 11);
	private final Font stateNumbersFont = Font.font("Arial", FontWeight.BOLD, 9);

	private final Image stepValueGraphic;
	private final Image backValueGraphic;

	private int lastClickedState = -1;

	private final Consumer<Integer> jumpConsumer = (i) -> traceExplorer.jump(traceExtractor.getState(i));
	private final Supplier<Integer> lastClickedStateSupplier = () -> lastClickedState;

	private boolean stateColoration;

	private static final int H_MARGIN = 8;
	private static final int V_MARGIN = 2;
	private static final int DIAMETER = 24;
	private static final int V_HEIGHT = 8;
	private static final int UNIT = DIAMETER + 2 * H_MARGIN;
	private static final Insets MARGIN_INSETS = new Insets(V_MARGIN, H_MARGIN, V_MARGIN, H_MARGIN);
	private static final Insets HALF_MARGIN_INSETS = new Insets(V_MARGIN, H_MARGIN / 2, V_MARGIN, H_MARGIN / 2);
	private static final Background HEADER_BACKGROUND = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));
	private static final Background BODY_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, null, null));
	private static final Background TRANSPARENT_BACKGROUND = new Background(
			new BackgroundFill(Color.TRANSPARENT, null, null));
	private static final Paint LINE_PAINT = new Color(Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(),
			Color.LIGHTGRAY.getBlue(), 0.5);
	private static final Background LINE_BACKGROUND = new Background(new BackgroundFill(LINE_PAINT, null, null));

	private Path statesGrid = null;
	private Rectangle highlightRectangle = null;

	private boolean scrollLock = false;
	private final Pane statesPane = new Pane();

	private final Image playGraphic;
	private final Image replayGraphic;

	private final BooleanProperty isInReplayMode;

	private static final int CURRENT_FORWARD_STEP = 0;
	private static final int CURRENT_BACKWARD_STEP = 1;
	private static final int CURRENT_BIGSTEP = 2;

	private Consumer<List<Boolean>> displayMenu = null;

	public MultidimensionalTimelineRenderer() {
		headerPane = new VBox();
		valuesLines = new VBox();
		bodyPane = new Pane();
		bodyScrollPane = new ScrollPane(bodyPane);
		backValueGraphic = new Image("/icons/nav_backward.gif");
		stepValueGraphic = new Image("/icons/nav_forward.gif");
		playGraphic = new Image("/icons/start_task.gif");
		replayGraphic = new Image("/icons/restart_task.gif");

		valueTitleWidth = new SimpleDoubleProperty();
		statesPaneHeight = new SimpleDoubleProperty();
		displayGrid = new SimpleBooleanProperty();
		isInReplayMode = new SimpleBooleanProperty();

		nbDisplayableStates = new SimpleIntegerProperty();
		nbDisplayableStates.bind(headerPane.widthProperty().divide(UNIT));
		nbStates = new SimpleIntegerProperty(0);
		currentState = new SimpleIntegerProperty();
		currentStep = new SimpleIntegerProperty(0);
		visibleStatesRange = new SimpleIntegerProperty();
		visibleStatesRange.bind(nbStates.add(1).subtract(nbDisplayableStates));

		nbDisplayableStates.addListener((v, o, n) -> {
			refresh();
		});
		currentState.addListener((v, o, n) -> {
			refresh();
		});
		currentStep.addListener((v, o, n) -> {
			refresh();
		});
		visibleStatesRange.addListener((v, o, n) -> {
			if (currentState.intValue() >= visibleStatesRange.intValue()) {
				currentState.set(visibleStatesRange.intValue() - 1);
			}
		});

		bodyScrollPane.setFitToWidth(true);
		bodyScrollPane.setBorder(Border.EMPTY);
		bodyScrollPane.setBackground(BODY_BACKGROUND);
		bodyScrollPane.setVisible(false);
		bodyPane.setBackground(BODY_BACKGROUND);

		statesPane.minHeightProperty().bind(statesPaneHeight);
		statesPane.heightProperty().addListener((v, o, n) -> {
			if (n.doubleValue() > statesPaneHeight.doubleValue()) {
				statesPaneHeight.set(n.doubleValue());
			}
		});
		headerPane.minWidthProperty().bind(widthProperty());
		headerPane.maxWidthProperty().bind(widthProperty());
		valuesLines.minWidthProperty().bind(widthProperty());
		valuesLines.maxWidthProperty().bind(widthProperty());
		valuesLines.getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				List<? extends Node> l = c.getList();
				int i = 0;
				for (Node n : l) {
					Pane p = (Pane) n;
					if (i % 2 == 1) {
						p.setBackground(LINE_BACKGROUND);
					} else {
						p.setBackground(TRANSPARENT_BACKGROUND);
					}
					i++;
				}
			}
		});

		for (int i = 0; i < 5; i++) {
			final double x1 = Math.max(-0.5 + 0.25 * i, 0);
			final double y1 = Math.max(0.5 - 0.25 * i, 0);
			final double x2 = Math.min(0.5 + 0.25 * i, 1);
			final double y2 = Math.min(1.5 - 0.25 * i, 1);
			final MoveTo move = new MoveTo(x1 * DIAMETER, y1 * DIAMETER);
			final LineTo line = new LineTo(x2 * DIAMETER, y2 * DIAMETER);
			diagonalHatching.getElements().addAll(move, line);
		}

		headerPane.setBackground(HEADER_BACKGROUND);
		valuesLines.setBackground(TRANSPARENT_BACKGROUND);
		setBackground(BODY_BACKGROUND);

		setupStatesPane();

		bodyPane.getChildren().add(valuesLines);
		bodyScrollPane.translateYProperty().bind(headerPane.heightProperty());
		bodyScrollPane.maxHeightProperty().bind(heightProperty().subtract(headerPane.heightProperty()));

		getChildren().add(headerPane);
		getChildren().add(bodyScrollPane);
		minHeightProperty().bind(headerPane.heightProperty().add(bodyScrollPane.heightProperty()));
		prefHeightProperty().bind(headerPane.heightProperty().add(bodyScrollPane.heightProperty()));
		maxHeightProperty().bind(headerPane.heightProperty().add(bodyScrollPane.heightProperty()));
	}

	private void showState(State<?, ?> state, boolean jump) {
		int toShow = Math.min(nbStates.intValue() - 1, Math.max(0, traceExtractor.getStateIndex(state)));
		int effectiveToShow = Math.min(visibleStatesRange.intValue() - 1,
				Math.max(0, toShow - nbDisplayableStates.intValue() / 2));
		if (jump) {
			traceExplorer.jump(traceExtractor.getState(effectiveToShow));
		}
		currentState.set(effectiveToShow);
	}

	private Pane setupStatesPane() {
		final Label titleLabel = new Label("All execution states (0)");
		nbStates.addListener((v, o, n) -> {
			String s = "All execution states (" + n.intValue() + ")";
			Platform.runLater(() -> {
				titleLabel.setText(s);
				titleLabel.setContentDisplay(ContentDisplay.RIGHT);
				final ImageView nodeGraphic = new ImageView();
				nodeGraphic.setImage(playGraphic);
				titleLabel.setGraphic(nodeGraphic);
				isInReplayMode.addListener((val, old, neu) -> {
					if (old != neu) {
						if (neu) {
							nodeGraphic.setImage(replayGraphic);
						} else {
							nodeGraphic.setImage(playGraphic);
						}
					}
				});
			});
		});
		titleLabel.setFont(statesFont);
		VBox.setMargin(titleLabel, HALF_MARGIN_INSETS);
		titleLabel.setAlignment(Pos.CENTER);
		final ScrollBar scrollBar = new ScrollBar();
		scrollBar.setVisibleAmount(1);
		scrollBar.setBlockIncrement(10);
		scrollBar.setMin(0);
		final IntegerBinding statesRange = visibleStatesRange.subtract(1);
		scrollBar.disableProperty().bind(statesRange.lessThanOrEqualTo(0));
		scrollBar.maxProperty().bind(statesRange);
		scrollBar.valueProperty().addListener((v, o, n) -> {
			if (o.intValue() != n.intValue() && n.intValue() != currentState.intValue()) {
				currentState.set(n.intValue());
			}
		});
		currentState.addListener((v, o, n) -> {
			if (o.intValue() != n.intValue() && n.intValue() != scrollBar.valueProperty().intValue()) {
				scrollBar.setValue(n.intValue());
			}
		});
		final HBox hBox = new HBox();
		final Polygon arrow = new Polygon(2.5, 10, 10, 5, 2.5, 0);
		HBox.setMargin(arrow, HALF_MARGIN_INSETS);
		final Label toggleValuesLabel = new Label("Timeline for dynamic information	");
		toggleValuesLabel.setFont(statesFont);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.getChildren().addAll(arrow, toggleValuesLabel);
		hBox.setCursor(Cursor.HAND);
		hBox.setOnMouseClicked((e) -> {
			if (bodyScrollPane.isVisible()) {
				bodyScrollPane.setVisible(false);
				arrow.setRotate(0);
			} else {
				bodyScrollPane.setVisible(true);
				arrow.setRotate(90);
			}
		});
		VBox.setMargin(hBox, HALF_MARGIN_INSETS);
		headerPane.getChildren().addAll(scrollBar, titleLabel, statesPane, hBox);
		VBox.setMargin(statesPane, MARGIN_INSETS);

		return headerPane;
	}

	private Pane setupValuePane(Dimension<?> dimension, Label titleLabel, Pane contentPane) {
		final HBox titlePane = new HBox();
		final VBox valueVBox = new VBox();
		final Node backValueGraphicNode = new ImageView(backValueGraphic);
		final double buttonScale = 0.66;
		backValueGraphicNode.setScaleX(1 / buttonScale);
		backValueGraphicNode.setScaleY(1 / buttonScale);
		final Button backValue = new Button("", backValueGraphicNode);
		backValue.setOnAction((e) -> {
			traceExplorer.backValue(dimension);
		});
		backValue.setScaleX(buttonScale);
		backValue.setScaleY(buttonScale);
		backValue.setDisable(!traceExplorer.canBackValue(dimension));
		final Node stepValueGraphicNode = new ImageView(stepValueGraphic);
		stepValueGraphicNode.setScaleX(1 / buttonScale);
		stepValueGraphicNode.setScaleY(1 / buttonScale);
		final Button stepValue = new Button("", stepValueGraphicNode);
		stepValue.setOnAction((e) -> {
			traceExplorer.stepValue(dimension);
		});
		stepValue.setDisable(!traceExplorer.canStepValue(dimension));
		stepValue.setScaleX(buttonScale);
		stepValue.setScaleY(buttonScale);
		titlePane.setAlignment(Pos.CENTER_LEFT);
		VBox.setMargin(titlePane, HALF_MARGIN_INSETS);
		VBox.setMargin(contentPane, MARGIN_INSETS);

		final CheckBox showValueCheckBox = new CheckBox();
		showValueCheckBox.setScaleX(buttonScale);
		showValueCheckBox.setScaleY(buttonScale);
		boolean hide = traceExtractor.isDimensionIgnored(dimension);
		if (hide) {
			showValueCheckBox.setSelected(false);
		} else {
			showValueCheckBox.setSelected(true);
		}
		BooleanProperty sel = showValueCheckBox.selectedProperty();
		backValue.visibleProperty().bind(sel);
		stepValue.visibleProperty().bind(sel);
		sel.addListener((v, o, n) -> {
			if (o != n) {
				traceExtractor.ignoreDimension(dimension, !n);
				if (n) {
					valueVBox.getChildren().add(contentPane);
				} else {
					valueVBox.getChildren().remove(contentPane);
				}
				sortValueLines();
			}
		});
		titlePane.getChildren().addAll(showValueCheckBox, titleLabel, backValue, stepValue);
		valueVBox.getChildren().add(titlePane);
		if (!hide) {
			valueVBox.getChildren().add(contentPane);
		}

		valuesLines.getChildren().add(valueVBox);
		valueVBox.setUserData(dimension);
		titleLabel.minWidthProperty().bind(valueTitleWidth);
		titleLabel.widthProperty().addListener((v, o, n) -> {
			if (n.doubleValue() > valueTitleWidth.get()) {
				valueTitleWidth.set(n.doubleValue());
			}
		});
		if (titleLabel.widthProperty().doubleValue() > valueTitleWidth.get()) {
			valueTitleWidth.set(titleLabel.widthProperty().doubleValue());
		}

		return valueVBox;
	}

	private HBox createStateTraceLine() {
		final HBox hBox = new HBox();
		statesPane.getChildren().add(hBox);
		headerPane.setFocusTraversable(true);
		return hBox;
	}

	private HBox createValueTraceLine(Dimension<?> dimension) {
		final HBox hBox = new HBox();
		final String title = traceExtractor.getDimensionLabel(dimension) + "  ";
		final Label titleLabel = new Label(title);
		titleLabel.setFont(valuesFont);
		final Pane pane = setupValuePane(dimension, titleLabel, hBox);
		pane.setFocusTraversable(true);
		return hBox;
	}

	private String computeStateLabel(int stateNumber) {
		if (stateNumber > 999) {
			return (stateNumber / 1000) + "k" + ((stateNumber % 1000) / 100);
		} else {
			return "" + stateNumber;
		}
	}

	private void fillStateLine(HBox line, List<State<?, ?>> states, int selectedState) {
		final Color currentColor = Color.CORAL;
		final Color otherColor = Color.SLATEBLUE;
		final int height = DIAMETER;
		final int width = DIAMETER;
		final int currentStateIndex = Math.max(0, currentState.intValue());
		final int diff = states.isEmpty() ? 0 : currentStateIndex - traceExtractor.getStateIndex(states.get(0));

		final List<List<Integer>> colorGroups = stateColoration ? computeColorGroups(states) : Collections.emptyList();
		final int nbColors = colorGroups.size();
		final List<Color> colorPalette = new ArrayList<>();
		if (nbColors > 0) {
			double interval = 360. / nbColors;
			for (int i = currentStateIndex % nbColors; i < nbColors; i++) {
				colorPalette.add(Color.hsb(i * interval, 0.75, 0.70));
			}
			for (int i = 0; i < currentStateIndex % nbColors; i++) {
				colorPalette.add(Color.hsb(i * interval, 0.75, 0.70));
			}
		}

		final int[] stateToColor = new int[states.size()];
		for (int i = 0; i < nbColors; i++) {
			final List<Integer> stateGroup = colorGroups.get(i);
			for (Integer state : stateGroup) {
				stateToColor[state % stateToColor.length] = i;
			}
		}

		line.getChildren().clear();
		if (diff > 0) {
			line.setTranslateX(-(UNIT * diff));
		}

		for (State<?, ?> state : states) {
			final int stateIndex = traceExtractor.getStateIndex(state);
			final Rectangle rectangle;
			if (selectedState == stateIndex) {
				rectangle = new Rectangle(width, height, currentColor);
			} else {
				if (stateColoration && !colorPalette.isEmpty()) {
					final int idx = stateToColor[stateIndex % stateToColor.length];
					if (idx != -1) {
						rectangle = new Rectangle(width, height, colorPalette.get(idx));
					} else {
						rectangle = new Rectangle(width, height, otherColor);
					}
				} else {
					rectangle = new Rectangle(width, height, otherColor);
				}
			}

			rectangle.setArcHeight(height);
			rectangle.setArcWidth(width);
			rectangle.setUserData(state);
			rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				if (e.getClickCount() > 1 && e.getButton() == MouseButton.PRIMARY) {
					traceExplorer.jump(state);
				}
				if (e.getClickCount() == 1 && e.getButton() == MouseButton.SECONDARY) {
					lastClickedState = stateIndex;
					final List<Boolean> enabledItems = new ArrayList<>();
					enabledItems.add(traceExtractor.isStateBreakable(state));
					enabledItems.add(true);
					displayMenu.accept(enabledItems);
				}
			});

			displayGridBinding = displayGridBinding.or(rectangle.hoverProperty());

			final String s = traceExtractor.getStateDescription(state);
			final Tooltip t = new Tooltip(s);
			Tooltip.install(rectangle, t);
			Label text = new Label(computeStateLabel(stateIndex));
			text.setTextOverrun(OverrunStyle.ELLIPSIS);
			text.setAlignment(Pos.CENTER);
			text.setMouseTransparent(true);
			text.setTextFill(Color.WHITE);
			text.setFont(stateNumbersFont);
			text.setMaxWidth(width);
			StackPane layout = new StackPane();
			StackPane.setMargin(rectangle, MARGIN_INSETS);
			if (!traceExtractor.isStateBreakable(state)) {
				Shape hatching = Shape.intersect(rectangle, diagonalHatching);
				hatching.setFill(Color.LIGHTGRAY);
				layout.getChildren().addAll(rectangle, hatching, text);
			} else {
				layout.getChildren().addAll(rectangle, text);
			}
			line.getChildren().add(layout);
		}
	}

	private void fillValueLine(HBox line, Dimension<?> dimension, int start, int end, int selectedState) {
		final List<Value<?>> values = traceExtractor.getValuesForStates(dimension, start, end);
		final Color currentColor = Color.DARKORANGE;
		final Color otherColor = Color.DARKBLUE;
		final int height = V_HEIGHT;

		line.getChildren().clear();

		final int currentStateIndex = Math.max(0, currentState.intValue());
		int stateIndex = currentStateIndex;

		int diff = values.isEmpty() ? 0 : currentStateIndex - traceExtractor.getValueFirstStateIndex(values.get(0));

		if (diff > 0) {
			line.setTranslateX(-(UNIT * diff));
		}

		for (Value<?> value : values) {
			final int firstStateIndex = traceExtractor.getValueFirstStateIndex(value);
			final int lastStateIndex = traceExtractor.getValueLastStateIndex(value);

			if (firstStateIndex > stateIndex) {
				// When the first visible value starts after the first state,
				// we fill the space with a transparent rectangle.
				int width = DIAMETER + UNIT * (firstStateIndex - stateIndex - 1);
				final Rectangle rectangle = new Rectangle(width, height, Color.TRANSPARENT);
				line.getChildren().add(rectangle);
				HBox.setMargin(rectangle, MARGIN_INSETS);
			}

			final Rectangle rectangle;
			final int width = DIAMETER + UNIT * (lastStateIndex - firstStateIndex);
			if (selectedState >= firstStateIndex && selectedState <= lastStateIndex) {
				rectangle = new Rectangle(width, height, currentColor);
			} else {
				rectangle = new Rectangle(width, height, otherColor);
			}
			rectangle.setArcHeight(height);
			rectangle.setArcWidth(DIAMETER / 2);
			rectangle.setUserData(value);
			rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				if (e.getClickCount() > 1 && e.getButton() == MouseButton.PRIMARY) {
					traceExplorer.jump(value);
				}
			});

			displayGridBinding = displayGridBinding.or(rectangle.hoverProperty());

			final String s = traceExtractor.getValueDescription(value);
			final Tooltip t = new Tooltip(s);
			Tooltip.install(rectangle, t);
			line.getChildren().add(rectangle);
			HBox.setMargin(rectangle, MARGIN_INSETS);

			stateIndex = lastStateIndex + 1;
		}
	}

	private NumberExpression createSteps(Step<?> step, int depth, int currentStateIndex, int selectedStateIndex, int firstStateIndex, int lastStateIndex,
			List<Path> accumulator, Object[] stepTargets) {

		final int stepStartingIndex = traceExtractor.getStateIndex(step.getStartingState());

		final boolean endedStep = step.getEndingState() != null;

		final int startingIndex = stepStartingIndex - currentStateIndex;
		final int endingIndex = (endedStep ? traceExtractor.getStateIndex(step.getEndingState())
				: nbStates.intValue()) - currentStateIndex;
		final Path path = new Path();
		path.setStrokeWidth(2);

		final double x1 = startingIndex * UNIT + UNIT / 2;
		final double x4 = endingIndex * UNIT + UNIT / 2;
		final double x2 = x1 + UNIT / 4;
		final double x3 = x4 - UNIT / 4;
		final double baseLineY = DIAMETER / 2 + V_MARGIN;
		final MoveTo moveTo = new MoveTo(x1, baseLineY);
		final LineTo lineTo = new LineTo(x2, baseLineY);
		final HLineTo hLineTo = new HLineTo(x3);
		path.getElements().addAll(moveTo, lineTo, hLineTo);
		if (endedStep) {
			final LineTo lastLineTo = new LineTo(x4, baseLineY);
			path.getElements().add(lastLineTo);
		}

		accumulator.add(path);

		final List<? extends Step<?>> subSteps = traceExtractor.getSubSteps(step);
		NumberExpression yOffset = new SimpleDoubleProperty(0);
		if (subSteps != null && !subSteps.isEmpty()) {
			for (Step<?> subStep : subSteps) {
				if (subStep.getStartingState() != subStep.getEndingState()
//						&& ((traceExtractor.getStateIndex(subStep.getEndingState()) < firstStateIndex) || traceExtractor.getStateIndex(subStep.getStartingState()) > lastStateIndex)
						) {
					yOffset = Bindings.max(yOffset, createSteps(subStep, depth + 1, currentStateIndex, selectedStateIndex, firstStateIndex, lastStateIndex, accumulator, stepTargets));
				}
			}
		}

		lineTo.yProperty().bind(yOffset.add(DIAMETER / 2 + V_MARGIN));

		if (stepTargets[CURRENT_FORWARD_STEP] == step) {
			path.setStroke(Color.DARKORANGE);
		} else if (stepTargets[CURRENT_BACKWARD_STEP] == step) {
			path.setStroke(Color.DARKGREEN);
		} else if (stepTargets[CURRENT_BIGSTEP] == step) {
			path.setStroke(Color.DARKRED);
		} else {
			path.setStroke(Color.DARKBLUE);
			if (!traceExplorer.getCallStack().contains(step) && (stepStartingIndex > selectedStateIndex
					|| (stepStartingIndex == selectedStateIndex && endedStep))) {
				path.getStrokeDashArray().addAll(5., 5.);
				path.setStrokeLineCap(StrokeLineCap.ROUND);
			}
		}

		return lineTo.yProperty();
	}

	private void sortValueLines() {
		final Map<Dimension<?>, Node> map = new HashMap<>();
		List<Node> lines = valuesLines.getChildren();
		final List<Node> nodes = new ArrayList<>();
		final List<Node> hiddenNodes = new ArrayList<>();
		lines.forEach(n -> map.put((Dimension<?>) n.getUserData(), n));
		lines.clear();
		traceExtractor.getDimensions().forEach(d -> {
			final Node n = map.get(d);
			if (traceExtractor.isDimensionIgnored(d)) {
				hiddenNodes.add(n);
			} else {
				nodes.add(n);
			}
		});
		lines.addAll(nodes);
		lines.addAll(hiddenNodes);
	}

	public void refresh() {
		Platform.runLater(() -> {
			valuesLines.getChildren().clear();
			statesPane.getChildren().clear();
			displayGrid.unbind();

			if (traceExplorer == null) {
				return;
			}

			isInReplayMode.set(traceExplorer.isInReplayMode());

			final int currentStateStartIndex = Math.max(0, currentState.intValue());
			final int currentStateEndIndex = Math.min(currentStateStartIndex + nbDisplayableStates.intValue(),
					traceExtractor.getStatesTraceLength());

			final int selectedStateIndex = traceExtractor.getStateIndex(traceExplorer.getCurrentState());

			displayGridBinding = new BooleanBinding() {
				@Override
				protected boolean computeValue() {
					return false;
				}
			};

			{
				final HBox hBox = createStateTraceLine();
				fillStateLine(hBox, traceExtractor.getStates(currentStateStartIndex - 1, currentStateEndIndex + 1),
						selectedStateIndex);
			}

			final List<Dimension<?>> dimensions = new ArrayList<>(traceExtractor.getDimensions());
			for (Dimension<?> dimension : dimensions) {
				final HBox hBox = createValueTraceLine(dimension);
				fillValueLine(hBox, dimension, currentStateStartIndex, currentStateEndIndex + 1, selectedStateIndex);
			}

			sortValueLines();

			displayGrid.bind(displayGridBinding);

			// ---------------- Steps creation

			final List<Step<?>> rootSteps = traceExtractor.getSteps(currentStateStartIndex - 1, currentStateEndIndex + 1);

			final List<Path> steps = new ArrayList<>();

			final Object[] stepTargets = new Object[3];

			Step<?> tmp = traceExplorer.getCurrentForwardStep();
			if (tmp != null) {
				stepTargets[CURRENT_FORWARD_STEP] = tmp;
			}
			tmp = traceExplorer.getCurrentBackwardStep();
			if (tmp != null) {
				stepTargets[CURRENT_BACKWARD_STEP] = tmp;
			}
			tmp = traceExplorer.getCurrentBigStep();
			if (tmp != null) {
				stepTargets[CURRENT_BIGSTEP] = tmp;
			}

			for (Step<?> step : rootSteps) {
				if (step.getStartingState() != step.getEndingState()) {
					createSteps(step, 0, currentStateStartIndex, selectedStateIndex, currentStateStartIndex - 1, currentStateEndIndex + 1, steps, stepTargets);
				}
			}

			statesPane.getChildren().addAll(0, steps);

			// ---------------- Adding grid and highlight rectangle

			if (statesGrid != null) {
				bodyPane.getChildren().remove(statesGrid);
			}
			if (highlightRectangle != null) {
				bodyPane.getChildren().remove(highlightRectangle);
			}

			statesGrid = new Path();
			final VLineTo vLineTo = new VLineTo();
			vLineTo.yProperty().bind(valuesLines.heightProperty());
			displayGrid.addListener((v, o, n) -> {
				if (n) {
					statesGrid.setStroke(Color.GRAY);
				} else {
					statesGrid.setStroke(Color.LIGHTGRAY);
				}
			});
			highlightRectangle = new Rectangle();
			for (int i = currentStateStartIndex; i <= currentStateEndIndex; i++) {
				if (i == selectedStateIndex) {
					highlightRectangle.setX(H_MARGIN + (i - currentStateStartIndex) * (2 * H_MARGIN + DIAMETER));
					highlightRectangle.setWidth(2 * H_MARGIN + DIAMETER);
					highlightRectangle.heightProperty().bind(valuesLines.heightProperty());
				}
				statesGrid.getElements().addAll(
						new MoveTo(H_MARGIN + (i - currentStateStartIndex) * (2 * H_MARGIN + DIAMETER), 0), vLineTo);
			}
			statesGrid.getStrokeDashArray().addAll(10., 10.);
			statesGrid.setStrokeWidth(1);
			statesGrid.setStroke(Color.LIGHTGRAY);
			statesGrid.setStrokeLineCap(StrokeLineCap.ROUND);
			bodyPane.getChildren().add(0, statesGrid);
			highlightRectangle.setFill(Color.LIGHTGRAY);
			bodyPane.getChildren().add(0, highlightRectangle);
		});
	}

	public void setScrollLock(boolean value) {
		scrollLock = value;
	}

	public void setStateColoration(boolean value) {
		stateColoration = value;
		refresh();
	}

	public Consumer<Integer> getJumpConsumer() {
		return jumpConsumer;
	}

	public void setTraceExplorer(
			ITraceExplorer<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer) {
		if (this.traceExplorer != null) {
			this.traceExplorer.removeListener(this);
		}
		this.traceExplorer = traceExplorer;
		if (this.traceExplorer != null) {
			this.traceExplorer.registerCommand(this, () -> update());
		}
		update();
	}

	public void setTraceExtractor(
			ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor) {
		this.traceExtractor = traceExtractor;
	}

	@Override
	public void update() {
		// TODO divide update between trace explorer listening (for the current
		// state)
		// and trace model listening (for the view of the trace) ?
		if (traceExplorer != null) {
			nbStates.set(traceExtractor.getStatesTraceLength());
			if (!scrollLock) {
				showState(traceExplorer.getCurrentState(), false);
			}
		} else {
			nbStates.set(0);
		}
		refresh();
	}

	public void dimensionsAdded(List<List<? extends EObject>> dimensions) {
		// TODO Auto-generated method stub
	}

	public void setMenuDisplayer(Consumer<List<Boolean>> displayMenu) {
		this.displayMenu = displayMenu;
	}

	public Supplier<Integer> getLastClickedStateSupplier() {
		return lastClickedStateSupplier;
	}

	private List<List<Integer>> computeColorGroups(List<State<?, ?>> states) {
		return traceExtractor.computeStateEquivalenceClasses().stream().sorted((l1, l2) -> {
			final int min1 = l1.stream().map(e -> traceExtractor.getStateIndex(e)).min((i1, i2) -> i1 - i2).get();
			final int min2 = l2.stream().map(e -> traceExtractor.getStateIndex(e)).min((i1, i2) -> i1 - i2).get();
			return min1 - min2;
		}).map(l -> l.stream().filter(s -> states.contains(s)).map(e -> traceExtractor.getStateIndex(e))
				.collect(Collectors.toList())).collect(Collectors.toList());
	}
}
