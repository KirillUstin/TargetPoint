package com.kirk.targetpoint;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetPoint implements ModInitializer {
	public static final String MOD_ID = "targetpoint";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("TargetPoint loaded successfully");
	}
}