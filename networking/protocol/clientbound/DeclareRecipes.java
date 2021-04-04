package networking.protocol.clientbound;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import networking.stream.MinecraftInputBuffer;
import util.recipe.Recipe;

// Packet ID 0x5A | S->C
public class DeclareRecipes {
    private static final Logger log = Logger.getLogger(DeclareRecipes.class.getName());
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

	static {
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);

		log.setLevel(Level.FINER);
		consoleHandler.setLevel(Level.FINER);
	}

    private DeclareRecipes() {}

    public static void execute(MinecraftInputBuffer buffer) throws IOException {
        int numOfRecipes = buffer.readVarInt();
        Recipe[] recipes = new Recipe[numOfRecipes];

        log.log(Level.FINE, "Recipes({0}):", numOfRecipes);

        for (int i = 0; i < recipes.length; i++) {
            recipes[i] = buffer.readRecipe();

            log.log(Level.FINE, " [{0}]\n", recipes[i]);
        }


    }
    
}