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

    public Object getValue(String key,Object ... args) {
        switch (key) {
            case "privileged": {
                if(args.length == 0) return null;
                return ((List<String>)values.get(key)).contains(args[0]);
            }
            default: {
                return values.get(key);
            }
        }
    }

    @Override
    public HashMap<String,Object> setDefaultValues() {
        HashMap<String,Object> values = new HashMap<>();
        values.put("minecraftVersion",PulsedParty.MINECRAFT_VERSION);
        values.put("privileged", Arrays.asList(""));
        values.put("privilegedKeepInventory", true);
        values.put("homes",true);
        values.put("privilegedDeathDrops",true);
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

    public void saveAll() {
        saveConfig();
        saveHomes();
    }

    public void addPrivilileged(UUID uuid) {
        ((List<String>)values.get("privileged")).add(uuid.toString());
        Home bedHome;
        Location bedLocation = Bukkit.getOfflinePlayer(uuid).getBedSpawnLocation();
        if(!Objects.isNull(bedLocation)) {
            bedHome = new Home("bed", new SimplifiedLocation(bedLocation));
        } else {
            bedHome = new Home("bed",new SimplifiedLocation(Bukkit.getWorlds().get(0).getSpawnLocation()));
        }
        List<Home> homesList = new ArrayList<>();
        homesList.add(bedHome);
        homes.put(uuid.toString(),homesList);
        saveConfig();
        saveHomes();
    }

    public void removePrivileged(UUID uuid) {
        ((List<String>)values.get("privileged")).remove(uuid.toString());
        homes.remove(uuid.toString());
        saveConfig();
        saveHomes();
    }

    public boolean isPrivileged(Player player) {
        return (boolean)getValue("privileged",player.getUniqueId().toString());
    }

    public void addHome(Player player, String name, Location location) {
        homes.get(player.getUniqueId().toString()).add(new Home(name,new SimplifiedLocation(location)));
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
