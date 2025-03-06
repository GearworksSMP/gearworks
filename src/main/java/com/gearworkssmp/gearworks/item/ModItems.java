package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;
import com.gearworkssmp.gearworks.items.BeeHoodie;
import com.gearworkssmp.gearworks.items.BetaHat;
import com.gearworkssmp.gearworks.items.BlueRabbitVisorHat;
import com.gearworkssmp.gearworks.items.CandyCane;
import com.gearworkssmp.gearworks.items.CogCupcake;
import com.gearworkssmp.gearworks.items.CorruptedExplorerHat;
import com.gearworkssmp.gearworks.items.DonutHat;
import com.gearworkssmp.gearworks.items.EchoingCore;
import com.gearworkssmp.gearworks.items.Ectoplasm;
import com.gearworkssmp.gearworks.items.EctoplasmTransitional;
import com.gearworkssmp.gearworks.items.Eggnog;
import com.gearworkssmp.gearworks.items.EmeraldTopHat;
import com.gearworkssmp.gearworks.items.EnderFragment;
import com.gearworkssmp.gearworks.items.ExplorerHat;
import com.gearworkssmp.gearworks.items.FrozenFruitPopsicle;
import com.gearworkssmp.gearworks.items.Fruitcake;
import com.gearworkssmp.gearworks.items.GingerbreadCog;
import com.gearworkssmp.gearworks.items.HolidayHam;
import com.gearworkssmp.gearworks.items.IncompleteCogCupcake;
import com.gearworkssmp.gearworks.items.InfusedStew;
import com.gearworkssmp.gearworks.items.JackOLantern;
import com.gearworkssmp.gearworks.items.LaserSword;
import com.gearworkssmp.gearworks.items.MegaMask;
import com.gearworkssmp.gearworks.items.MulledWine;
import com.gearworkssmp.gearworks.items.NoBetaHat;
import com.gearworkssmp.gearworks.items.RoastedChestnuts;
import com.gearworkssmp.gearworks.items.ShadowEssence;
import com.gearworkssmp.gearworks.items.SnowberryPie;
import com.gearworkssmp.gearworks.items.SteampunkSantaHat;
import com.gearworkssmp.gearworks.items.SwoltersLargeDumbbellBlockItem;
import com.gearworkssmp.gearworks.items.TrickOrTreatCandy;

import com.gearworkssmp.gearworks.items.WinterStew;
import com.gearworkssmp.gearworks.items.WitchHat;

import com.gearworkssmp.gearworks.items.WitchRobes;

import com.gearworkssmp.gearworks.items.materials.LaserSwordMaterial;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.gearworkssmp.gearworks.item.ModBlocks.*;

public class ModItems {

	public static final Item BETA_HAT = registerItem("beta_hat", new BetaHat(new FabricItemSettings()));
	public static final Item NO_BETA_HAT = registerItem("no_beta_hat", new NoBetaHat(new FabricItemSettings()));
	public static final Item COG_CUPCAKE = registerItem("cog_cupcake", new CogCupcake(new FabricItemSettings().food(ModFoodComponent.COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP1 = registerItem("cog_cupcake_step1", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP2 = registerItem("cog_cupcake_step2", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP3 = registerItem("cog_cupcake_step3", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item ECHOING_CORE = registerItem("echoing_core", new EchoingCore(new FabricItemSettings()));
	public static final Item ECTOPLASM = registerItem("ectoplasm", new Ectoplasm(new FabricItemSettings()));
	public static final Item ECTOPLASM_TRANSITIONAL = registerItem("ectoplasm_transitional", new EctoplasmTransitional(new FabricItemSettings()));
	public static final Item ENDER_FRAGMENT = registerItem("ender_fragment", new EnderFragment(new FabricItemSettings()));
	public static final Item INFUSED_STEW = registerItem("infused_stew", new InfusedStew(new FabricItemSettings()));
	public static final Item JACK_O_LANTERN = registerItem("jack_o_lantern", new JackOLantern(new FabricItemSettings()));
	public static final Item SHADOW_ESSENCE = registerItem("shadow_essence", new ShadowEssence(new FabricItemSettings()));
	public static final Item TRICK_OR_TREAT_CANDY = registerItem("trick_or_treat_candy", new TrickOrTreatCandy(new FabricItemSettings().food(ModFoodComponent.TRICK_OR_TREAT_CANDY)));
	public static final Item MEGA_MASK = registerItem("mega_mask", new MegaMask(new FabricItemSettings()));
	public static final Item DONUT_HAT = registerItem("donut_hat", new DonutHat(new FabricItemSettings()));
	public static final Item WITCH_HAT = registerItem("witch_hat", new WitchHat(new FabricItemSettings()));
	public static final Item WITCH_ROBES = registerItem("witch_robes", new WitchRobes(new FabricItemSettings()));
	public static final Item GREEN_PRESENT_BLOCK_ITEM = registerItem("green_present", new BlockItem(ModBlocks.GREEN_PRESENT_BLOCK, new FabricItemSettings()));
	public static final Item RED_PRESENT_BLOCK_ITEM = registerItem("red_present", new BlockItem(ModBlocks.RED_PRESENT_BLOCK, new FabricItemSettings()));
	public static final Item BEE_HOODIE = registerItem("bee_hoodie", new BeeHoodie(new FabricItemSettings()));
	public static final Item BLUE_RABBIT_VISOR_HAT = registerItem("blue_rabbit_visor_hat", new BlueRabbitVisorHat(new FabricItemSettings()));
	public static final Item EMERALD_TOP_HAT = registerItem("emerald_top_hat", new EmeraldTopHat(new FabricItemSettings()));
	public static final Item STEAMPUNK_SANTA_HAT = registerItem("steampunk_santa_hat", new SteampunkSantaHat(new FabricItemSettings()));
	public static final Item CANDY_CANE = registerItem("candy_cane", new CandyCane(new FabricItemSettings().food(ModFoodComponent.CANDY_CANE)));
	public static final Item EGGNOG = registerItem("eggnog", new Eggnog(new FabricItemSettings().food(ModFoodComponent.EGGNOG)));
	public static final Item FROZEN_FRUIT_POPSICLE = registerItem("frozen_fruit_popsicle", new FrozenFruitPopsicle(new FabricItemSettings().food(ModFoodComponent.FROZEN_FRUIT_POPSICLE)));
	public static final Item FRUITCAKE = registerItem("fruitcake", new Fruitcake(new FabricItemSettings().food(ModFoodComponent.FRUITCAKE)));
	public static final Item GINGERBREAD_COG = registerItem("gingerbread_cog", new GingerbreadCog(new FabricItemSettings().food(ModFoodComponent.GINGERBREAD_COG)));
	public static final Item HOLIDAY_HAM = registerItem("holiday_ham", new HolidayHam(new FabricItemSettings().food(ModFoodComponent.HOLIDAY_HAM)));
	public static final Item MULLED_WINE = registerItem("mulled_wine", new MulledWine(new FabricItemSettings().food(ModFoodComponent.MULLED_WINE)));
	public static final Item ROASTED_CHESTNUTS = registerItem("roasted_chestnuts", new RoastedChestnuts(new FabricItemSettings().food(ModFoodComponent.ROASTED_CHESTNUTS)));
	public static final Item SNOWBERRY_PIE = registerItem("snowberry_pie", new SnowberryPie(new FabricItemSettings().food(ModFoodComponent.SNOWBERRY_PIE)));
	public static final Item WINTER_STEW = registerItem("winter_stew", new WinterStew(new FabricItemSettings().food(ModFoodComponent.WINTER_STEW)));
	public static final Item CANDY_CANE_BLOCK_ITEM = registerItem("candy_cane_block", new BlockItem(CANDY_CANE_BLOCK, new Item.Settings()));
	public static final Item CHOCOLATE_BLOCK_ITEM = registerItem("chocolate_block", new BlockItem(CHOCOLATE_BLOCK, new Item.Settings()));
	public static final Item DARK_CHOCOLATE_BLOCK_ITEM = registerItem("dark_chocolate_block", new BlockItem(DARK_CHOCOLATE_BLOCK, new Item.Settings()));
	public static final Item MILK_CHOCOLATE_BLOCK_ITEM = registerItem("milk_chocolate_block", new BlockItem(MILK_CHOCOLATE_BLOCK, new Item.Settings()));
	public static final Item TECHNICAL_MASTERY_TROPHY_ITEM = registerItem("technical_mastery_trophy", new BlockItem(TECHNICAL_MASTERY_TROPHY_BLOCK, new Item.Settings()));
	public static final Item MOST_ENTERTAINING_TROPHY_ITEM = registerItem("most_entertaining_trophy", new BlockItem(MOST_ENTERTAINING_TROPHY_BLOCK, new Item.Settings()));
	public static final Item ARTISTIC_EXCELLENCE_TROPHY_ITEM = registerItem("artistic_excellence_trophy", new BlockItem(ARTISTIC_EXCELLENCE_TROPHY_BLOCK, new Item.Settings()));
	public static final Item EXPLORER_HAT = registerItem("explorer_hat", new ExplorerHat(new FabricItemSettings()));
	public static final Item CORRUPT_EXPLORER_HAT = registerItem("corrupt_explorer_hat", new CorruptedExplorerHat(new FabricItemSettings()));
	public static final Item SWOLTERS_LARGE_DUMBBELL = registerItem("swolters_large_dumbbell", new SwoltersLargeDumbbellBlockItem(SWOLTERS_LARGE_DUMBBELL_BLOCK, new Item.Settings()));
	public static final Item LASERSWORD = registerItem("lasersword", new LaserSword(new LaserSwordMaterial(), -5, -1.0F, new FabricItemSettings()));

	private static void addItemsToCombatItemGroup(FabricItemGroupEntries group) {
		group.add(BETA_HAT);
		group.add(NO_BETA_HAT);
		group.add(JACK_O_LANTERN);
		group.add(MEGA_MASK);
		group.add(DONUT_HAT);
		group.add(WITCH_HAT);
		group.add(WITCH_ROBES);
		group.add(BEE_HOODIE);
		group.add(BLUE_RABBIT_VISOR_HAT);
		group.add(EMERALD_TOP_HAT);
		group.add(STEAMPUNK_SANTA_HAT);
		group.add(EXPLORER_HAT);
		group.add(CORRUPT_EXPLORER_HAT);
		group.add(SWOLTERS_LARGE_DUMBBELL);
		group.add(LASERSWORD);
	}

	private static void addItemsToFoodItemGroup(FabricItemGroupEntries group) {
		group.add(COG_CUPCAKE);
		group.add(TRICK_OR_TREAT_CANDY);
		group.add(CANDY_CANE);
		group.add(EGGNOG);
		group.add(FROZEN_FRUIT_POPSICLE);
		group.add(FRUITCAKE);
		group.add(GINGERBREAD_COG);
		group.add(HOLIDAY_HAM);
		group.add(MULLED_WINE);
		group.add(ROASTED_CHESTNUTS);
		group.add(SNOWBERRY_PIE);
		group.add(WINTER_STEW);
	}

	private static void addItemsToIngredientsItemGroup(FabricItemGroupEntries group) {
		group.add(ECHOING_CORE);
		group.add(ECTOPLASM);
		group.add(ENDER_FRAGMENT);
		group.add(SHADOW_ESSENCE);
	}

	private static void addItemsToBuildingBlockItemGroup(FabricItemGroupEntries group) {
		group.add(GREEN_PRESENT_BLOCK_ITEM);
		group.add(RED_PRESENT_BLOCK_ITEM);
		group.add(CANDY_CANE_BLOCK_ITEM);
		group.add(CHOCOLATE_BLOCK_ITEM);
		group.add(DARK_CHOCOLATE_BLOCK_ITEM);
		group.add(MILK_CHOCOLATE_BLOCK_ITEM);
	}

	private static Item registerItem(String name, Item item) {
		if (item instanceof TrinketRenderer) {
			Gearworks.LOGGER.info("Registering renderer " + Gearworks.ID + ":" + name);
			TrinketRendererRegistry.registerRenderer(item, (TrinketRenderer) item);
		}
		return Registry.register(Registries.ITEM, new Identifier(Gearworks.ID, name), item);
	}

	public static void registerModItems() {
		Gearworks.LOGGER.info("Registering Mod Items for " + Gearworks.ID);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemsToFoodItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::addItemsToBuildingBlockItemGroup);
	}

	;
}
