package net.favouriteless.modopedia.neoforge.datagen.providers;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MItemModelProvider extends ItemModelProvider {

	public static final String[] METALS = { "brass", "copper", "gold", "iron", "silver" };
	public static final String[] COLOURS = { "brown", "red", "green", "blue", "purple" };

	public MItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Modopedia.MOD_ID, existingFileHelper);
	}


	@Override
	protected void registerModels() {
		for(String metal : METALS) {
			for(String colour : COLOURS) {
				defaultBook(colour + "_" + metal);
			}
		}
		simpleItem("book", "brown_brass");
	}

	private void simpleItem(String name) {
		simpleItem(name, name);
	}

	private void defaultBook(String name) {
		simpleItem(Modopedia.MOD_ID + "_books/" + name, name);
	}

	private void simpleItem(String modelName, String textureName) {
		withExistingParent(ITEM_FOLDER + "/" + modelName, mcLoc(ITEM_FOLDER + "/generated")).texture("layer0", ITEM_FOLDER + "/" + textureName);
	}

}



