package org.gemoc.sigpmlextended.k3dsa;

import cnrs.luchogie.up.SwingPlotter;
import cnrs.luchogie.up.data.Figure;
import com.google.common.collect.LinkedListMultimap;
import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;
import sigpmlextended.Agent;
import sigpmlextended.InputPort;
import sigpmlextended.Port;
import org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties;
import org.gemoc.sigpmlextended.k3dsa.InputPortAspect;
import org.gemoc.sigpmlextended.k3dsa.NamedElementAspect;
import org.gemoc.sigpmlextended.k3dsa.SystemAspect;

@Aspect(className = Agent.class)
@SuppressWarnings("all")
public class AgentAspect extends NamedElementAspect {
  public static void isExecuting(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_isExecuting(_self_, _self);
  }
  
  public static void execute(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_execute(_self_, _self);
  }
  
  private static SwingPlotter plotter(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    Object result = null;
    result =_privk3_plotter(_self_, _self);
    return (cnrs.luchogie.up.SwingPlotter)result;
  }
  
  private static void plotter(final Agent _self, final SwingPlotter plotter) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_plotter(_self_, _self,plotter);
  }
  
  private static JFrame frame(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    Object result = null;
    result =_privk3_frame(_self_, _self);
    return (javax.swing.JFrame)result;
  }
  
  private static void frame(final Agent _self, final JFrame frame) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_frame(_self_, _self,frame);
  }
  
  private static Figure figure(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    Object result = null;
    result =_privk3_figure(_self_, _self);
    return (cnrs.luchogie.up.data.Figure)result;
  }
  
  private static void figure(final Agent _self, final Figure figure) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_figure(_self_, _self,figure);
  }
  
  public static int currentExecCycle(final Agent _self) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    Object result = null;
    result =_privk3_currentExecCycle(_self_, _self);
    return (int)result;
  }
  
  public static void currentExecCycle(final Agent _self, final int currentExecCycle) {
    org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectProperties _self_ = org.gemoc.sigpmlextended.k3dsa.AgentAspectAgentAspectContext.getSelf(_self);
    _privk3_currentExecCycle(_self_, _self,currentExecCycle);
  }
  
  protected static void _privk3_isExecuting(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
    String _name = _self.getName();
    String _plus = (_name + "\n      isExecuting (");
    int _currentExecCycle = AgentAspect.currentExecCycle(_self);
    String _plus_1 = (_plus + Integer.valueOf(_currentExecCycle));
    String _plus_2 = (_plus_1 + ")");
    InputOutput.<String>println(_plus_2);
    int _currentExecCycle_1 = AgentAspect.currentExecCycle(_self);
    int _plus_3 = (_currentExecCycle_1 + 1);
    AgentAspect.currentExecCycle(_self, _plus_3);
  }
  
  protected static void _privk3_execute(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
    String _name = _self.getName();
    String _plus = (_name + "\n      execute (");
    int _currentExecCycle = AgentAspect.currentExecCycle(_self);
    String _plus_1 = (_plus + Integer.valueOf(_currentExecCycle));
    String _plus_2 = (_plus_1 + ")");
    InputOutput.<String>println(_plus_2);
    int _currentExecCycle_1 = AgentAspect.currentExecCycle(_self);
    String _plus_3 = ("@ : " + Integer.valueOf(_currentExecCycle_1));
    String _plus_4 = (_plus_3 + " sharedMemory:");
    sigpmlextended.System _system = NamedElementAspect.getSystem(_self);
    LinkedListMultimap _sharedMemory = SystemAspect.sharedMemory(_system);
    String _plus_5 = (_plus_4 + _sharedMemory);
    InputOutput.<String>println(_plus_5);
    final ArrayList<String> outputPortNames = CollectionLiterals.<String>newArrayList();
    JFrame _frame = AgentAspect.frame(_self);
    SwingPlotter _plotter = AgentAspect.plotter(_self);
    _frame.setContentPane(_plotter);
    JFrame _frame_1 = AgentAspect.frame(_self);
    _frame_1.setSize(400, 400);
    final Binding binding = new Binding();
    SwingPlotter _plotter_1 = AgentAspect.plotter(_self);
    binding.setVariable("plotter", _plotter_1);
    JFrame _frame_2 = AgentAspect.frame(_self);
    binding.setVariable("frame", _frame_2);
    final int localTime = AgentAspect.currentExecCycle(_self);
    binding.setVariable("localTime", Integer.valueOf(localTime));
    Figure _figure = AgentAspect.figure(_self);
    binding.setVariable("figure", _figure);
    EList<Port> _ownedPorts = _self.getOwnedPorts();
    for (final Port p : _ownedPorts) {
      if ((p instanceof InputPort)) {
        final ArrayList<Object> params = CollectionLiterals.<Object>newArrayList();
        int _rate = ((InputPort)p).getRate();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _rate, true);
        for (final Integer i : _doubleDotLessThan) {
          {
            sigpmlextended.System _system_1 = NamedElementAspect.getSystem(_self);
            LinkedListMultimap _sharedMemory_1 = SystemAspect.sharedMemory(_system_1);
            String _plus_6 = ("start for rate params sharedMemory: " + _sharedMemory_1);
            InputOutput.<String>println(_plus_6);
            sigpmlextended.System _system_2 = NamedElementAspect.getSystem(_self);
            LinkedListMultimap _sharedMemory_2 = SystemAspect.sharedMemory(_system_2);
            String _name_1 = ((InputPort)p).getName();
            List _get = _sharedMemory_2.get(_name_1);
            final Object tmp = _get.get(0);
            params.add(tmp);
            sigpmlextended.System _system_3 = NamedElementAspect.getSystem(_self);
            LinkedListMultimap _sharedMemory_3 = SystemAspect.sharedMemory(_system_3);
            String _name_2 = ((InputPort)p).getName();
            _sharedMemory_3.remove(_name_2, tmp);
            sigpmlextended.System _system_4 = NamedElementAspect.getSystem(_self);
            LinkedListMultimap _sharedMemory_4 = SystemAspect.sharedMemory(_system_4);
            String _plus_7 = ("end for rate params sharedMemory: " + _sharedMemory_4);
            InputOutput.<String>println(_plus_7);
          }
        }
        InputOutput.<String>println(("   in params: " + params));
        String _name_1 = ((InputPort)p).getName();
        binding.setVariable(_name_1, params);
      } else {
        String _name_2 = p.getName();
        outputPortNames.add(_name_2);
      }
    }
    EList<Port> _ownedPorts_1 = _self.getOwnedPorts();
    for (final Port p_1 : _ownedPorts_1) {
      if ((p_1 instanceof InputPort)) {
        InputPortAspect.sizeToread(((InputPort)p_1), 0);
      }
    }
    try {
      final ClassLoader ucl = AgentAspect.class.getClassLoader();
      final GroovyShell shell = new GroovyShell(ucl, binding);
      String _code = _self.getCode();
      Object _evaluate = shell.evaluate(_code);
      final Map<String, Object> res = ((Map<String, Object>) _evaluate);
      boolean _containsValue = res.containsValue("figure");
      if (_containsValue) {
        Figure _figure_1 = AgentAspect.figure(_self);
        Object _get = res.get("figure");
        _figure_1.addFigure(((Figure) _get));
      }
      for (final String portName : outputPortNames) {
        sigpmlextended.System _system_1 = NamedElementAspect.getSystem(_self);
        LinkedListMultimap _sharedMemory_1 = SystemAspect.sharedMemory(_system_1);
        Object _get_1 = res.get(portName);
        _sharedMemory_1.put(portName, _get_1);
      }
      sigpmlextended.System _system_2 = NamedElementAspect.getSystem(_self);
      LinkedListMultimap _sharedMemory_2 = SystemAspect.sharedMemory(_system_2);
      String _plus_6 = ("sharedMemory: " + _sharedMemory_2);
      InputOutput.<String>println(_plus_6);
    } catch (final Throwable _t) {
      if (_t instanceof MultipleCompilationErrorsException) {
        final MultipleCompilationErrorsException cnfe = (MultipleCompilationErrorsException)_t;
        String _message = cnfe.getMessage();
        String _plus_7 = ("Failed to call Groovy script" + _message);
        InputOutput.<String>println(_plus_7);
        InputOutput.<String>println("figure not correctly updated");
        InputOutput.<String>println("using default values for system.sharedMemory instead of computed ones");
        for (final String portName_1 : outputPortNames) {
          sigpmlextended.System _system_3 = NamedElementAspect.getSystem(_self);
          LinkedListMultimap _sharedMemory_3 = SystemAspect.sharedMemory(_system_3);
          _sharedMemory_3.put(portName_1, Double.valueOf(0.0));
        }
        cnfe.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  protected static SwingPlotter _privk3_plotter(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
     return _self_.plotter; 
  }
  
  protected static void _privk3_plotter(final AgentAspectAgentAspectProperties _self_, final Agent _self, final SwingPlotter plotter) {
    _self_.plotter = plotter; try {
    
    			for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    				if (m.getName().equals("set" + "Plotter")
    						&& m.getParameterTypes().length == 1) {
    					m.invoke(_self, plotter);
    
    				}
    			}
    		} catch (Exception e) {
    			// Chut !
    		} 
  }
  
  protected static JFrame _privk3_frame(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
     return _self_.frame; 
  }
  
  protected static void _privk3_frame(final AgentAspectAgentAspectProperties _self_, final Agent _self, final JFrame frame) {
    _self_.frame = frame; try {
    
    			for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    				if (m.getName().equals("set" + "Frame")
    						&& m.getParameterTypes().length == 1) {
    					m.invoke(_self, frame);
    
    				}
    			}
    		} catch (Exception e) {
    			// Chut !
    		} 
  }
  
  protected static Figure _privk3_figure(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
     return _self_.figure; 
  }
  
  protected static void _privk3_figure(final AgentAspectAgentAspectProperties _self_, final Agent _self, final Figure figure) {
    _self_.figure = figure; try {
    
    			for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    				if (m.getName().equals("set" + "Figure")
    						&& m.getParameterTypes().length == 1) {
    					m.invoke(_self, figure);
    
    				}
    			}
    		} catch (Exception e) {
    			// Chut !
    		} 
  }
  
  protected static int _privk3_currentExecCycle(final AgentAspectAgentAspectProperties _self_, final Agent _self) {
     return _self_.currentExecCycle; 
  }
  
  protected static void _privk3_currentExecCycle(final AgentAspectAgentAspectProperties _self_, final Agent _self, final int currentExecCycle) {
    _self_.currentExecCycle = currentExecCycle; try {
    
    			for (java.lang.reflect.Method m : _self.getClass().getMethods()) {
    				if (m.getName().equals("set" + "CurrentExecCycle")
    						&& m.getParameterTypes().length == 1) {
    					m.invoke(_self, currentExecCycle);
    
    				}
    			}
    		} catch (Exception e) {
    			// Chut !
    		} 
  }
}