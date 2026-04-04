package me.dexrn.conversiondebugger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ornithemc.osl.entrypoints.api.ModInitializer;

public class ConversionDebugger implements ModInitializer {
	public static final String MOD_NAME = "ConversionDebugger";
	public static final String MOD_ID = "conversiondebugger";

	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	@Override
	public void init() {
		LOGGER.info("Hello!");
	}
}
