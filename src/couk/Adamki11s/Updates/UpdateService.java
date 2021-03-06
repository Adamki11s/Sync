package couk.Adamki11s.Updates;

import java.util.HashSet;

import org.bukkit.plugin.Plugin;

import couk.Adamki11s.Exceptions.MultipleUpdatePackageException;

public class UpdateService {

	private static HashSet<UpdatePackage> services = new HashSet<UpdatePackage>();

	/**
	 * Register your plugins update service.
	 * @param updatePackage
	 * @throws MultipleUpdatePackageException only one update package per plugin is allowed.
	 */
	public static void registerUpdateService(UpdatePackage updatePackage) throws MultipleUpdatePackageException {
		if (isUpdateServiceRegistered(updatePackage.getPlugin())) {
			throw new MultipleUpdatePackageException(updatePackage.getPlugin());
		} else {
			services.add(updatePackage);
		}
	}
	
	protected static HashSet<UpdatePackage> getServices(){
		return services;
	}

	/**
	 * Check whether a plugin has an update service registered.
	 * @param p
	 * @return
	 */
	public static boolean isUpdateServiceRegistered(Plugin p) {
		for (UpdatePackage up : services) {
			if (up.getPlugin().getDescription().getName().equalsIgnoreCase(p.getDescription().getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove a plugins update service.
	 * @param p
	 */
	public static void removeUpdateService(Plugin p) {
		UpdatePackage removal = null;
		for(UpdatePackage pack : services){
			if (pack.getPlugin().getDescription().getName().equalsIgnoreCase(p.getDescription().getName())) {
				removal = pack;
				break;
			}
		}
		if(removal == null){
			throw new NullPointerException("Could not find an update package for plugin " + p.getDescription().getName());
		} else {
			services.remove(removal);
		}
	}

	/**
	 * Modify the settings of a plugins update package.
	 * @param newPackage
	 * @param p
	 */
	public static void editUpdatePackage(UpdatePackage newPackage, Plugin p) {
		if(!isUpdateServiceRegistered(p)){
			throw new NullPointerException("Could not find an update package for plugin " + p.getDescription().getName());
		} else {
			removeUpdateService(p);
			try {
				registerUpdateService(newPackage);
			} catch (MultipleUpdatePackageException e) {
				e.printStackTrace();
			}
		}
	}

}
