package net.favouriteless.modopedia.client;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

	public static final ClientConfig INSTANCE;
	public static final ModConfigSpec SPEC;


	private ClientConfig(ModConfigSpec.Builder builder) {
	}

	static {
		Pair<ClientConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ClientConfig::new);
		INSTANCE = pair.getLeft();
		SPEC = pair.getRight();
	}

}