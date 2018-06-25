package org.iMage.plugins;

import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Knows all available plug-ins and is responsible for using the service loader API to detect them.
 *
 * @author Dominic Wolff
 *
 */
public final class PluginManager {
	
    private static ServiceLoader<JmjrstPlugin> loader;

  /**
   * No constructor for utility class.
   */
  private PluginManager() {
    throw new IllegalAccessError();
  }

  /**
   * Return an {@link Iterable} Object with all available {@link JmjrstPlugin}s alphabetically sorted 
   * according to their class names.
   *
   * @return an {@link Iterable} Object containing all available plug-ins alphabetically sorted 
   *          according to their class names.
   */
  public static Iterable<JmjrstPlugin> getPlugins() {
	  loader = ServiceLoader.load(JmjrstPlugin.class);
	  
	  Iterator<JmjrstPlugin> availablePlugins;
	  List<JmjrstPlugin> sortedPlugins;
	  
	  try {
		  availablePlugins = loader.iterator();
		  sortedPlugins = new ArrayList<>();
		  availablePlugins.forEachRemaining(sortedPlugins::add);
		  Collections.sort(sortedPlugins);
		  System.out.println("PluginManager: found and loaded " + sortedPlugins.size() + " plugins.");
	  } catch (ServiceConfigurationError serviceError) {
          sortedPlugins = null;
          serviceError.printStackTrace();
      }
	  return sortedPlugins;
  }

}
