package fr.nebulo9.pulsedparty;

import com.google.common.reflect.TypeToken;
import fr.nebulo9.pulsarlib.PLConfig;
import fr.nebulo9.pulsarlib.location.SimplifiedLocation;
import fr.nebulo9.pulsarlib.message.Message;
import fr.nebulo9.pulsarlib.util.FileUtil;
import fr.nebulo9.pulsedparty.util.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class PPConfig extends PLConfig {

    private File homesFile;
    private HashMap<String, List<Home>> homes;
    private File joinMessageFile;
    private String joinMessage;

    public PPConfig() {
        super();
        homesFile = new File(instance.getDataFolder().getPath() + File.separator + "homes.json");
        FileUtil.createFile(homesFile);
        joinMessageFile = new File(instance.getDataFolder().getPath() + File.separator + "joinMessage.txt");
        FileUtil.createFile(joinMessageFile);
    }

    @Override
    public HashMap<String,Object> setDefaultValues() {
        HashMap<String,Object> values = new HashMap<>();
        values.put("minecraftVersion",PulsedParty.MINECRAFT_VERSION);
        values.put("privileged", Arrays.asList(""));
        values.put("privilegedKeepInventory", true);
        values.put("homes",true);
        return values;
    }

    public void loadJoinMessage() {
        joinMessage = FileUtil.readFromFile(joinMessageFile);
    }

    public void loadHomes() {
        Message.logInfo("Loading homes...");
        homes = GSON.fromJson(FileUtil.readFromFile(homesFile),new TypeToken<HashMap<String,List<Home>>>(){}.getType());
        Message.logInfo("Homes loaded successfully !");
    }

    public void saveConfig() {
        FileUtil.writeInfFile(configFile,values);
    }

    public void saveHomes() {
        FileUtil.writeInfFile(homesFile,homes);
    }

    public void addPrivilileged(UUID uuid) {
        ((List<String>)values.get("privileged")).add(uuid.toString());
        Home bedHome;
        Location bedLocation = Bukkit.getOfflinePlayer(uuid).getBedSpawnLocation();
        if(!Objects.isNull(bedLocation)) {
            bedHome = new Home("bed", SimplifiedLocation.simplify(bedLocation));
        } else {
            bedHome = new Home("bed",SimplifiedLocation.simplify(Bukkit.getWorlds().get(0).getSpawnLocation()));
        }
        homes.put(uuid.toString(),List.of(bedHome));
        saveConfig();
        saveHomes();
    }

    public void removePrivileged(UUID uuid) {
        ((List<String>)values.get("privileged")).remove(uuid.toString());
        homes.remove(uuid.toString());
        saveConfig();
        saveHomes();
    }

    public void addHome(Player player, String name, Location location) {
        homes.get(player.getUniqueId().toString()).add(new Home(name,SimplifiedLocation.simplify(location)));
        saveHomes();
    }

    public void removeHome(Player player,String name) {
        homes.get(player.getUniqueId().toString()).removeIf(home -> (home.getName().equals(name)));
    }

    public Home getHome(Player player,String name) {
        return homes.get(player.getUniqueId().toString()).stream().filter(home -> (home.getName().equals(name))).findAny().orElse(null);
    }

    public String getJoinMessage() {
        return joinMessage;
    }
}
