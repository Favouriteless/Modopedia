package net.favouriteless.modopedia.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {

	public static final ServerConfig INSTANCE;
	public static final ModConfigSpec SPEC;

	public final BooleanValue studyRequiresBooks;

	private ServerConfig(ModConfigSpec.Builder builder) {
		studyRequiresBooks = builder.comment("Study requires the book to be present in the player's inventory").define("study_requires_book", true);
	}

	static {
		Pair<ServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
		INSTANCE = pair.getLeft();
		SPEC = pair.getRight();
	}

}