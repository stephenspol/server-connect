package com.stephenspol.server.connect.networking.protocol;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.UUID;

import com.stephenspol.server.connect.networking.protocol.serverbound.login.*;
import com.stephenspol.server.connect.networking.protocol.serverbound.play.*;
import com.stephenspol.server.connect.networking.protocol.serverbound.status.*;
import com.stephenspol.server.connect.util.Slot;

public class ServerboundPacket {

    public static final String CLASS_NONEXIST = "Class does not exist for packet ID:0x";

    public static final Level packetInfo = Level.FINER;

    public static final Logger log = Logger.getLogger(ServerboundPacket.class.getName());
    public static final Logger err = Logger.getLogger(ServerboundPacket.class.getName() + 2);
    public static final ConsoleHandler consoleHandler = new ConsoleHandler();

    public static final FileHandler fileHandler;
    public static final FileHandler fileErrHandler;

    static {
        try {
            // Create logs directory if it does not exist
            new File("logs").mkdir();

            fileHandler = new FileHandler("logs/Serverbound Info Log - %u.txt");
            fileErrHandler = new FileHandler("logs/Serverbound Error Log - %u.txt");

            log.setUseParentHandlers(false);
            log.addHandler(consoleHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);

            err.setUseParentHandlers(false);
            fileErrHandler.setFormatter(new SimpleFormatter());
            err.addHandler(fileErrHandler);

            log.setLevel(Level.FINER);
            consoleHandler.setLevel(Level.INFO);
            fileHandler.setLevel(Level.FINER);

            err.setLevel(Level.WARNING);
            fileErrHandler.setLevel(Level.WARNING);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);   
        }
    }

    private int state;

    public ServerboundPacket(int state) {
        this.state = state;
    }

    public byte[] execute(int packetId, Object... params) throws IOException {
        switch (state) {
            case 1:
                return Status.getById(packetId).execute();

            case 2:
                return Login.getById(packetId).execute(params);

            case 3:
                return Play.getById(packetId).execute(params);

            default:
                throw new IllegalArgumentException("State " + state + " is a invalid!");
        } 
    }

    public void setState(int state) {
        this.state = state;
    }

    public enum Status {
        REQUEST(0x00, Request.class),
        PING(0x01, Ping.class);

        private final int id;
        private Class<?> cls;

        private static final Status[] BY_ID = values();

        Status(int id, Class<?> cls) {
            this.id = id;
            this.cls = cls;
        }

        public int getId() {
            return id;
        }

        public byte[] execute() throws IOException{
            if (cls == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", cls.getName());
                Method method = cls.getMethod("execute");

                // Call null because method is static
                return (byte[]) method.invoke(null);
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

    public enum Login {
        LOGIN_START(0x00, LoginStart.class, String.class),
        ENCRYPTION_RESPONSE(0x01, EncryptionResponse.class, byte.class, byte.class),
        LOGIN_PLUGIN_RESPONSE(0x02, LoginPluginResponse.class, int.class, byte.class);

        private final int id;
        private Class<?> cls;
        private Class<?>[] args;

        private static final Login[] BY_ID = values();

        Login(int id, Class<?> cls, Class<?>... args) {
            this.id = id;
            this.cls = cls;
            this.args = args;
        }

        public int getId() {
            return id;
        }

        public byte[] execute(Object... params) throws IOException{
            if (cls == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", cls.getName());
                Method method = cls.getMethod("execute", args);

                // Call null because method is static
                return (byte[]) method.invoke(null, params);
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

    public enum Play {
        TELEPORT_CONFIRM(0x00, TeleportConfirm.class, int.class),
        QUERY_BLOCK_NBT(0x01, QueryBlockNBT.class, int.class, int[].class),
        SET_DIFFICULTY(0x02, SetDifficulty.class, byte.class),
        CHAT_MESSAGE(0x03, ChatMessage.class, String.class),
        CLIENT_STATUS(0x04, ClientStatus.class, int.class),
        CLIENT_SETTINGS(0x05, ClientSettings.class, String.class, byte.class, int.class, boolean.class, short.class, int.class),
        TAB_COMPLETE(0x06, TabComplete.class, int.class, String.class),
        WINDOW_CONFIRM(0x07, WindowConfirm.class, byte.class, short.class, boolean.class),
        CLICK_WINDOW_BUTTON(0x08, ClickWindowButton.class, byte.class, byte.class),
        CLICK_WINDOW(0x09, ClickWindow.class, byte.class, short.class, byte.class, short.class, int.class, Slot.class),
        CLOSE_WINDOW(0x0A, CloseWindow.class, byte.class),
        PLUGIN_MESSAGE(0x0B, PluginMessage.class, String.class, byte[].class),
        EDIT_BOOK(0x0C, EditBook.class, Slot.class, boolean.class, int.class),
        QUERY_ENTITY_NBT(0x0D, QueryEntityNBT.class, int.class, int.class),
        INTERACT_ENTITY(0x0E, InteractEntity.class, int.class, int.class, float.class, float.class, float.class, int.class, boolean.class),
        GENERATE_STRUCTURE(0x0F, GenerateStructure.class, int[].class, int.class, boolean.class),
        KEEP_ALIVE(0x10, KeepAlive.class, long.class),
        LOCK_DIFFICULTY(0x11, LockDifficulty.class, boolean.class),
        PLAYER_POS(0x12, PlayerPos.class, double.class, double.class, double.class, boolean.class),
        PLAYER_POS_AND_ROT(0x13, PlayerPosAndRot.class, double.class, double.class, double.class, float.class, float.class, boolean.class),
        PLAYER_ROT(0x14, PlayerRot.class, float.class, float.class, boolean.class),
        PLAYER_MOVE(0x15, PlayerMove.class, boolean.class),
        VEHICLE_MOVE(0x16, VehicleMove.class, double.class, double.class, double.class, float.class, float.class),
        STEER_BOAT(0x17, SteerBoat.class, boolean.class, boolean.class),
        PICK_ITEM(0x18, PickItem.class, int.class),
        CRAFT_RECIPE_REQUEST(0x19, CraftRecipeRequest.class, byte.class, String.class, boolean.class),
        PLAYER_ABILITIES(0x1A, PlayerAbilities.class, byte.class),
        PLAYER_DIGGING(0x1B, PlayerDigging.class, int.class, int[].class, byte.class),
        ENTITY_ACTION(0x1C, EntityAction.class, int.class, int.class, int.class),
        STEER_VEHICLE(0x1D, SteerVehicle.class, float.class, float.class, byte.class),
        SET_RECIPE_BOOK_STATE(0x1E, SetRecipeBookState.class, int.class, boolean.class, boolean.class),
        SET_DISPLAYED_RECIPE(0x1F, SetDisplayedRecipe.class, String.class),
        NAME_ITEM(0x20, NameItem.class, String.class),
        RESOURCE_PACK_STATUS(0x21, ResourcePackStatus.class, int.class),
        ADVANCEMENT_TAB(0x22, AdvancementTab.class, int.class, String.class),
        SELECT_TRADE(0x23, SelectTrade.class, int.class),
        SET_BEACON_EFFECT(0x24, SetBeaconEffect.class, int.class, int.class),
        HELD_ITEM_CHANGE(0x25, HeldItemChange.class, short.class),
        UPDATE_COMMAND_BLOCK(0x26, UpdateCommandBlock.class, int[].class, String.class, int.class, byte.class),
        UPDATE_COMMAND_BLOCK_MINECART(0x27, UpdateCommandBlockMinecart.class, int.class, String.class, boolean.class),
        CREATIVE_INVENTORY_ACTION(0x28, CreativeInventoryAction.class, short.class, Slot.class),
        UPDATE_JIGSAW_BLOCK(0x29, UpdateJigsawBlock.class, int[].class, String.class, String.class, String.class, String.class, String.class),
        UPDATE_STRUCTURE_BLOCK(0x2A, UpdateStructureBlock.class, int[].class, int.class, int.class, String.class, byte.class, byte.class, byte.class,
                               byte.class, byte.class, byte.class, int.class, int.class, String.class, float.class, long.class, byte.class),
        UPDATE_SIGN(0x2B, UpdateSign.class, int[].class, String.class, String.class, String.class, String.class),
        ANIMATION(0x2C, Animation.class, int.class),
        SPECTATE(0x2D, Spectate.class, UUID.class),
        PLAYER_BLOCK_PLACEMENT(0x2E, PlayerBlockPlacement.class, int.class, int[].class, int.class, float.class, float.class, float.class, boolean.class),
        USE_ITEM(0x2F, UseItem.class, int.class);

        private final int id;
        private Class<?> cls;
        private Class<?>[] args;

        private static final Play[] BY_ID = values();

        Play(int id, Class<?> cls, Class<?>... args) {
            this.id = id;
            this.cls = cls;
            this.args = args;
        }

        public int getId() {
            return id;
        }

        public byte[] execute(Object... params) throws IOException{
            if (cls == null) {
                throw new IOException(CLASS_NONEXIST + Integer.toHexString(id).toUpperCase());
            }

            try {
                log.log(Level.FINE, "Class {0} being executed", cls.getName());
                Method method = cls.getMethod("execute", args);

                // Call null because method is static
                return (byte[]) method.invoke(null, params);
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