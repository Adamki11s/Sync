package couk.Adamki11s.Sync;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import couk.Adamki11s.Configuration.FileConfigurations;
import couk.Adamki11s.Configuration.FolderConfigurations;
import couk.Adamki11s.Configuration.GlobalConfiguration;
import couk.Adamki11s.Exceptions.MultipleUpdatePackageException;
import couk.Adamki11s.Statistics.BitlyTracker;
import couk.Adamki11s.Statistics.RegisterCycle;
import couk.Adamki11s.Updates.UpdateCycle;
import couk.Adamki11s.Updates.UpdatePackage;
import couk.Adamki11s.Updates.UpdateService;

public class Sync extends JavaPlugin {

	public static Logger log = Logger.getLogger("Sync");
	public static String version;
	public static final String prefix = "[Sync]";
	public static Plugin plugin;
	BukkitTask updateCycleTaskId;
	private static BukkitTask statisticCycleTaskId;
	private final BitlyTracker tracker = new BitlyTracker();
	private final String trackURL = "http://bit.ly/ys1Q7x";

	@Override
	public void onDisable() {
		if (GlobalConfiguration.isCheckForUpdates()) {
			updateCycleTaskId.cancel();
			logGenericInfo("Update thread stopped.");
		}
		if (GlobalConfiguration.isAllowStatisticTracking()) {
			statisticCycleTaskId.cancel();
		}
		logGenericInfo(" Sync Version " + version + " un-loaded successfully.");
	}

	@Override
	public void onEnable() {
		logGenericInfo("Loading Sync...");
		//this.tracker.pingURL(this.trackURL);
		version = this.getDescription().getVersion();
		plugin = this;
		FolderConfigurations.folderChecks();
		FileConfigurations.createConfigurations();
		if (GlobalConfiguration.isCheckForUpdates()) {
			logGenericInfo("Checking for updates every " + GlobalConfiguration.getUpdateCycle() + " minutes.");
			updateCycleTaskId = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdateCycle(), 20L, (GlobalConfiguration.getUpdateCycle() * 60 * 20));
		} else {
			logGenericInfo("Sync Update checking disabled.");
		}
		if (GlobalConfiguration.isAllowStatisticTracking()) {
			logGenericInfo("Saving plugin statistics every " + GlobalConfiguration.getStatisticUpdateCycle() + " minutes.");
			statisticCycleTaskId = Bukkit.getServer().getScheduler()
					.runTaskTimerAsynchronously(this, new RegisterCycle(), 20L, (GlobalConfiguration.getStatisticUpdateCycle() * 60 * 20));
		} else {
			logGenericInfo("Sync Statistic tracking disabled.");
		}
		version = this.getDescription().getVersion();
		logGenericInfo(prefix + " Sync Version " + version + " loaded successfully.");
		logGenericInfo("Sync loaded successfully!");
		UpdatePackage pack = new UpdatePackage(plugin, "http://dev.bukkit.org/server-mods/sync/", GlobalConfiguration.isAutoDownloadUpdates(),
				GlobalConfiguration.isReloadAfterUpdate(), new File(FolderConfigurations.syncUpdates + File.separator + "Sync.jar"));
		try {
			UpdateService.registerUpdateService(pack);
		} catch (MultipleUpdatePackageException e) {
			e.printStackTrace();
		}
	}

	public static void logGenericInfo(String message) {
		log.info(prefix + " " + message);
	}

	public static void logGenericWarning(String message) {
		log.warning(prefix + " " + message);
	}

}
