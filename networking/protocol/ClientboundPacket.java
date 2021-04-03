package networking.protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputStream;
import networking.protocol.clientbound.*;

public enum ClientboundPacket {
    SPAWN_ENTITY(0x00),
    SPAWN_XP_ORB(0x01),
    SPAWN_LIVING_ENTITY(0x02),
    SPAWN_PAINTING(0x03),
    SPAWN_PLAYER(0x04),
    ENTITY_ANIMATION(0x05),
    STATISTICS(0x06),
    ACK_PLAYER_DIGGING(0x07),
    BLOCK_BREAK_ANIMATION(0x08),
    BLOCK_ENTITY_DATA(0x09),
    BLOCK_ACTION(0x0A),
    BLOCK_CHANGE(0x0B),
    BOSS_BAR(0x0C),
    SERVER_DIFFICULTY(0x0D, ServerDifficulty.class),
    CHAT_MESSAGE(0x0E),
    TAB_COMPLETE(0x0F),
    DELCARE_COMMAND(0x10),
    WINDOW_CONFIRM(0x11),
    CLOSE_WINDOW(0x12),
    WINDOW_ITEMS(0x13),
    WINDOW_PROPERTY(0x14),
    SET_SLOT(0x15),
    SET_COOLDOWN(0x16),
    PLUGIN_MESSAGE(0x17, PluginMessage.class),
    NAMED_SOUND_EFFECT(0x18),
    DISCONNECT(0x19),
    ENTITY_STATUS(0x1A),
    EXPLOSION(0x1B),
    UNLOAD_CHUNK(0x1C),
    CHANGE_GAME_STATE(0x1D),
    OPEN_HORSE_WINDOW(0x1E),
    KEEP_ALIVE(0x1F),
    CHUNK_DATA(0x20),
    EFFECT(0x21),
    PARTICLE(0x22),
    UPDATE_LIGHT(0x23),
    JOIN_GAME(0x24, JoinGame.class),
    MAP_DATA(0x25),
    TRADE_LIST(0x26),
    ENTITY_POS(0x27),
    ENTITY_POS_AND_ROTATION(0x28),
    ENTITY_ROTATION(0x29),
    ENTITY_MOVEMENT(0x2A),
    VEHICLE_MOVE(0x2B),
    OPEN_BOOK(0x2C),
    OPEN_WINDOW(0x2D),
    OPEN_SIGN_EDITOR(0x2E),
    CRAFT_RECIPE_RESPONSE(0x2F),
    PLAYER_ABILITIES(0x30, PlayerAbilities.class),
    COMBAT_EVENT(0x31),
    PLAYER_INFO(0x32),
    FACE_PLAYER(0x33),
    PLAYER_POS_AND_LOOK(0x34),
    UNLOCK_RECIPE(0x35),
    DESTROY_ENTITIES(0x36),
    REMOVE_ENTITY_EFFECT(0x37),
    RESOURCE_PACK_SEND(0x38),
    RESPAWN(0x39),
    ENTITY_HEAD_LOOK(0x3A),
    MULTI_BLOCK_CHANGE(0x3B),
    SELECT_ADVANCE_TAB(0x3C),
    WORLD_BORDER(0x3D),
    CAMERA(0x3E),
    HELD_ITEM_CHANGE(0x3F, HeldItemChange.class),
    UPDATE_VIEW_POS(0x40),
    UPDATE_VIEW_DIST(0x41),
    SPAWN_POS(0x42, SpawnPosition.class),
    DISPLAY_SCOREBOARD(0x43),
    ENTITY_METADATA(0x44),
    ATTACH_ENTITY(0x45),
    ENTITY_VEL(0x46),
    ENTITY_EQUIP(0x47),
    SET_XP(0x48),
    UPDATE_HEALTH(0x49),
    SCOREBOARD_OBJECTIVE(0x4A),
    SET_PASSENGERS(0x4B),
    TEAMS(0x4C),
    UPDATE_SCORE(0x4D),
    TIME_UPDATE(0x4E),
    TITLE(0x4F),
    ENTITY_SOUND_EFFECT(0x50),
    SOUND_EFFECT(0x51),
    STOP_SOUND(0x52),
    PLAYER_LIST(0x53),
    NBT_QUERY_RESPONSE(0x54),
    COLLECT_ITEM(0x55),
    ENTITY_TELEPORT(0x56),
    ADVANCEMENTS(0x57),
    ENTITY_PROPERTIES(0x58),
    ENTITY_EFFECT(0x59),
    DECLARE_RECIPES(0x5A, DeclareRecipes.class),
    TAGS(0x5B);

    protected static final Logger log = Logger.getLogger(ClientboundPacket.class.getName());
    protected static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private final int id;
    private Class<?> clazz;

    private static final ClientboundPacket[] BY_ID;

    static {
        BY_ID = new ClientboundPacket[0x5C];
        for (ClientboundPacket p : ClientboundPacket.values()) {
            BY_ID[p.getId()] = p;
        }
    }

    ClientboundPacket(int id) {
        this(id, null);
    }

    ClientboundPacket(int id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void execute(MinecraftInputStream in) throws IOException{
        if (clazz == null) {
            throw new IOException("Class does not exist for packet ID:0x" + Integer.toHexString(id).toUpperCase());
        }

        try {
            Method method = clazz.getMethod("execute", MinecraftInputStream.class);

            // Call null because method is static
            method.invoke(null, in);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
            throw new IOException("Error on Invoking method!", e);
        } catch (InvocationTargetException e) {
            throw new IOException("Method called a exception!", e);
        }
    }

    public static ClientboundPacket getById(int id) {
        if (id >= 0 && id < BY_ID.length) {
            ClientboundPacket packetId = BY_ID[id];
            if (packetId == null) {
                throw new IllegalArgumentException("Tag type id " + id + " is unknown!");
            }
            return packetId;
        } else {
            throw new IndexOutOfBoundsException("Tag type id " + id + " is out of bounds!");
        }
    }
}