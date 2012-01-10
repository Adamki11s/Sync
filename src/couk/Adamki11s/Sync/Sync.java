package couk.Adamki11s.Sync;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import couk.Adamki11s.Managers.SyncControl;

public class Sync extends JavaPlugin {
	
	public static Logger log = Logger.getLogger("Sync");
	public static String version = "1.0.0";
	public static final String prefix = "[Sync]";

	@Override
	public void onDisable() {
		log.info(prefix + " Sync Version " + version + " un-loaded successfully.");
	}

	@Override
	public void onEnable() {
		version = this.getDescription().getVersion();
		log.info(prefix + " Sync Version " + version + " loaded successfully.");
	}
	
	public static void logGenericInfo(String message){
		log.info(prefix + " " + message);
	}
	
	public SyncControl getSyncControl(){
		return new SyncControl();
	}

}
