package networking.protocol;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import networking.buffer.MinecraftInputBuffer;
import networking.protocol.clientbound.login.*;
import networking.protocol.clientbound.play.*;
import networking.protocol.clientbound.status.*;

public class ClientboundPacket {

    public static final String CLASS_NONEXIST = "Class does not exist for packet ID:0x";

    public static final Level packetInfo = Level.FINER;

    public static final Logger log = Logger.getLogger(ClientboundPacket.class.getName());
    public static final Logger err = Logger.getLogger(ClientboundPacket.class.getName() + 2);
    public static final ConsoleHandler consoleHandler = new ConsoleHandler();

    public static final FileHandler fileHandler;
    public static final FileHandler fileErrHandler;

    static {
        try {
            // Create logs directory if it does not exist
            new File("logs").mkdir();

            fileHandler = new FileHandler("logs/Info Log - %u.txt");
            fileErrHandler = new FileHandler("logs/Error Log - %u.txt");

            log.setUseParentHandlers(false);
            log.addHandler(consoleHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);

            err.setUseParentHandlers(false);
            fileErrHandler.setFormatter(new SimpleFormatter());
            err.addHandler(fileErrHandler);

            log.setLevel(Level.FINER);
            consoleHandler.setLevel(Level.FINER);
            fileHandler.setLevel(Level.FINER);

            err.setLevel(Level.WARNING);
            fileErrHandler.setLevel(Level.WARNING);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);   
        }
    }

    private int state;

    public ClientboundPacket(int state) {
        this.state = state;
    }

    public void execute(MinecraftInputBuffer buffer, int packetId) throws IOException {
        switch (state) {
            case 0:
                Status.getById(packetId).execute(buffer);
                break;

            case 1:
                Login.getById(packetId).execute(buffer);
                break;

            case 2:
                Play.getById(packetId).execute(buffer);
                break;

            default:
                throw new IllegalArgumentException("Status " + state + " is a invalid!");
        } 
    }

    public void setState(int state) {
        this.state = state;
    }

    enum Status {
        RESPONSE(0x00, Response.class),
        PONG(0x01, Pong.class);

        private final int id;
        private Class<?> clazz;

        private static final Status[] BY_ID = values();

        Status(int id, Class<?> clazz) {
            this.id = id;
            this.clazz = clazz;
        }

        public int getId() {
            return id;
        }

        public void execute(MinecraftInputBuffer buffer) throws IOException{
            if (clazz == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", clazz.getName());
                Method method = clazz.getMethod("execute", MinecraftInputBuffer.class);

                // Call null because method is static
                method.invoke(null, buffer);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                throw new IOException("Error on Invoking method!", e);
            } catch (InvocationTargetException e) {
                throw new IOException("Method called a exception!", e);
            }
        }

        public static Status getById(int id) {
            if (id >= 0 && id < BY_ID.length) {
                Status packetId = BY_ID[id];
                if (packetId == null) {
                    throw new IllegalArgumentException("Packet id " + id + " is unknown!");
                }
                return packetId;
            } else {
                throw new IndexOutOfBoundsException("Packet id " + id + " is out of bounds!");
            }
        }
    }

    enum Login {
        DISCONNECT(0x00, Disconnect.class),
        ENCRYPTION_REQUEST(0x01, EncryptionRequest.class),
        LOGIN_SUCCESS(0x02, LoginSuccess.class),
        SET_COMPRESSION(0x03, SetCompression.class),
        LOGIN_PLUGIN_REQUEST(0x04, LoginPluginRequest.class);


        private final int id;
        private Class<?> clazz;

        private static final Login[] BY_ID = values();

        Login(int id, Class<?> clazz) {
            this.id = id;
            this.clazz = clazz;
        }

        public int getId() {
            return id;
        }

        public void execute(MinecraftInputBuffer buffer) throws IOException{
            if (clazz == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", clazz.getName());
                Method method = clazz.getMethod("execute", MinecraftInputBuffer.class);

                // Call null because method is static
                method.invoke(null, buffer);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                throw new IOException("Error on Invoking method!", e);
            } catch (InvocationTargetException e) {
                throw new IOException("Method called a exception!", e);
            }
        }

        public static Login getById(int id) {
            if (id >= 0 && id < BY_ID.length) {
                Login packetId = BY_ID[id];
                if (packetId == null) {
                    throw new IllegalArgumentException("Packet id " + id + " is unknown!");
                }
                return packetId;
            } else {
                throw new IndexOutOfBoundsException("Packet id " + id + " is out of bounds!");
            }
        }
    }

    enum Play {
        SPAWN_ENTITY(0x00, SpawnEntity.class),
        SPAWN_XP_ORB(0x01, SpawnXPOrb.class),
        SPAWN_LIVING_ENTITY(0x02, SpawnLivingEntity.class),
        SPAWN_PAINTING(0x03, SpawnPainting.class),
        SPAWN_PLAYER(0x04, SpawnPlayer.class),
        ENTITY_ANIMATION(0x05, EntityAnimation.class),
        STATISTICS(0x06, Statistics.class),
        ACK_PLAYER_DIGGING(0x07, AckPlayerDigging.class),
        BLOCK_BREAK_ANIMATION(0x08, BlockBreakAnimation.class),
        BLOCK_ENTITY_DATA(0x09, BlockEntityData.class),
        BLOCK_ACTION(0x0A, BlockAction.class),
        BLOCK_CHANGE(0x0B, BlockChange.class),
        BOSS_BAR(0x0C, BossBar.class),
        SERVER_DIFFICULTY(0x0D, ServerDifficulty.class),
        CHAT_MESSAGE(0x0E, ChatMessage.class),
        TAB_COMPLETE(0x0F, TabComplete.class),
        DELCARE_COMMAND(0x10, DeclareCommands.class),
        WINDOW_CONFIRM(0x11, WindowConfirm.class),
        CLOSE_WINDOW(0x12, CloseWindow.class),
        WINDOW_ITEMS(0x13, WindowItems.class),
        WINDOW_PROPERTY(0x14, WindowProperty.class),
        SET_SLOT(0x15, SetSlot.class),
        SET_COOLDOWN(0x16, SetCooldown.class),
        PLUGIN_MESSAGE(0x17, PluginMessage.class),
        NAMED_SOUND_EFFECT(0x18, NamedSoundEffect.class),
        DISCONNECT(0x19, Disconnect.class),
        ENTITY_STATUS(0x1A, EntityStatus.class),
        EXPLOSION(0x1B, Explosion.class),
        UNLOAD_CHUNK(0x1C, UnloadChunk.class),
        CHANGE_GAME_STATE(0x1D, ChangeGameState.class),
        OPEN_HORSE_WINDOW(0x1E, OpenHorseWindow.class),
        KEEP_ALIVE(0x1F, KeepAlive.class),
        CHUNK_DATA(0x20, ChunkData.class),
        EFFECT(0x21, Effect.class),
        PARTICLE(0x22, Particle.class),
        UPDATE_LIGHT(0x23, UpdateLight.class),
        JOIN_GAME(0x24, JoinGame.class),
        MAP_DATA(0x25, MapData.class),
        TRADE_LIST(0x26, TradeList.class),
        ENTITY_POS(0x27, EntityPos.class),
        ENTITY_POS_AND_ROTATION(0x28, EntityPosAndRotation.class),
        ENTITY_ROTATION(0x29, EntityRotation.class),
        ENTITY_MOVEMENT(0x2A, EntityMovement.class),
        VEHICLE_MOVE(0x2B, VehicleMove.class),
        OPEN_BOOK(0x2C, OpenBook.class),
        OPEN_WINDOW(0x2D, OpenWindow.class),
        OPEN_SIGN_EDITOR(0x2E, OpenSignEditor.class),
        CRAFT_RECIPE_RESPONSE(0x2F, CraftRecipeResponse.class),
        PLAYER_ABILITIES(0x30, PlayerAbilities.class),
        COMBAT_EVENT(0x31, CombatEvent.class),
        PLAYER_INFO(0x32, PlayerInfo.class),
        FACE_PLAYER(0x33, FacePlayer.class),
        PLAYER_POS_AND_LOOK(0x34, PlayerPosAndLook.class),
        UNLOCK_RECIPE(0x35, UnlockRecipe.class),
        DESTROY_ENTITIES(0x36, DestoryEntities.class),
        REMOVE_ENTITY_EFFECT(0x37, RemoveEntityEffect.class),
        RESOURCE_PACK_SEND(0x38, ResourcePackSend.class),
        RESPAWN(0x39, Respawn.class),
        ENTITY_HEAD_LOOK(0x3A, EntityHeadLook.class),
        MULTI_BLOCK_CHANGE(0x3B, MultiBlockChange.class),
        SELECT_ADVANCE_TAB(0x3C, SelectAdvanceTab.class),
        WORLD_BORDER(0x3D, WorldBorder.class),
        CAMERA(0x3E, Camera.class),
        HELD_ITEM_CHANGE(0x3F, HeldItemChange.class),
        UPDATE_VIEW_POS(0x40, UpdateViewPos.class),
        UPDATE_VIEW_DIST(0x41, UpdateViewDist.class),
        SPAWN_POS(0x42, SpawnPosition.class),
        DISPLAY_SCOREBOARD(0x43, DisplayScoreboard.class),
        ENTITY_METADATA(0x44),
        ATTACH_ENTITY(0x45, AttachEntity.class),
        ENTITY_VEL(0x46, EntityVel.class),
        ENTITY_EQUIP(0x47, EntityEquip.class),
        SET_XP(0x48, SetXP.class),
        UPDATE_HEALTH(0x49, UpdateHealth.class),
        SCOREBOARD_OBJECTIVE(0x4A, ScoreboardObjective.class),
        SET_PASSENGERS(0x4B, SetPassengers.class),
        TEAMS(0x4C, Teams.class),
        UPDATE_SCORE(0x4D, UpdateScore.class),
        TIME_UPDATE(0x4E, TimeUpdate.class),
        TITLE(0x4F, Title.class),
        ENTITY_SOUND_EFFECT(0x50, EntitySoundEffect.class),
        SOUND_EFFECT(0x51, SoundEffect.class),
        STOP_SOUND(0x52, StopSound.class),
        PLAYER_LIST(0x53, PlayerList.class),
        NBT_QUERY_RESPONSE(0x54, NBTQueryResponse.class),
        COLLECT_ITEM(0x55, CollectItem.class),
        ENTITY_TELEPORT(0x56, EntityTeleport.class),
        ADVANCEMENTS(0x57, Advancements.class),
        ENTITY_PROPERTIES(0x58, EntityProperties.class),
        ENTITY_EFFECT(0x59, EntityEffect.class),
        DECLARE_RECIPES(0x5A, DeclareRecipes.class),
        TAGS(0x5B, Tags.class);

        private final int id;
        private Class<?> clazz;

        private static final Play[] BY_ID = values();

        /**
         * Used to intialize a Packet and assign a ID to it.
         * @deprecated Only used if packet is not assigned to a class
         * @param id Packet ID
         */
        @Deprecated(forRemoval = false)
        Play(int id) {
            this(id, null);
        }

        Play(int id, Class<?> clazz) {
            this.id = id;
            this.clazz = clazz;
        }

        public int getId() {
            return id;
        }

        public void execute(MinecraftInputBuffer buffer) throws IOException{
            if (clazz == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", clazz.getName());
                Method method = clazz.getMethod("execute", MinecraftInputBuffer.class);

                // Call null because method is static
                method.invoke(null, buffer);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                throw new IOException("Error on Invoking method!", e);
            } catch (InvocationTargetException e) {
                throw new IOException("Method called a exception!", e);
            }
        }

        public static Play getById(int id) {
            if (id >= 0 && id < BY_ID.length) {
                Play packetId = BY_ID[id];
                if (packetId == null) {
                    throw new IllegalArgumentException("Packet id " + id + " is unknown!");
                }
                return packetId;
            } else {
                throw new IndexOutOfBoundsException("Packet id " + id + " is out of bounds!");
            }
        }
    }
}