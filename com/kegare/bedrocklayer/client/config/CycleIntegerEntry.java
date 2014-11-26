/*
 * BedrockLayer
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.bedrocklayer.client.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.ButtonEntry;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.math.NumberUtils;

@SideOnly(Side.CLIENT)
public class CycleIntegerEntry extends ButtonEntry
{
	protected final int beforeValue;
	protected final int defaultValue;
	protected int currentValue;

	public CycleIntegerEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement)
	{
		super(owningScreen, owningEntryList, configElement);
		this.beforeValue = NumberUtils.toInt(configElement.get().toString());
		this.defaultValue = NumberUtils.toInt(configElement.getDefault().toString());
		this.currentValue = beforeValue;
		this.btnValue.enabled = enabled();

		updateValueButtonText();
	}

	@Override
	public void updateValueButtonText()
	{
		btnValue.displayString = I18n.format(configElement.getLanguageKey() + "." + currentValue);
	}

	@Override
	public void valueButtonPressed(int slotIndex)
	{
		if (enabled())
		{
			if (++currentValue > NumberUtils.toInt(configElement.getMaxValue().toString()))
			{
				currentValue = 0;
			}

			updateValueButtonText();
		}
	}

	@Override
	public boolean isDefault()
	{
		return currentValue == defaultValue;
	}

	@Override
	public void setToDefault()
	{
		if (enabled())
		{
			currentValue = defaultValue;

			updateValueButtonText();
		}
	}

	@Override
	public boolean isChanged()
	{
		return currentValue != beforeValue;
	}

	@Override
	public void undoChanges()
	{
		if (enabled())
		{
			currentValue = beforeValue;

			updateValueButtonText();
		}
	}

	@Override
	public boolean saveConfigElement()
	{
		if (enabled() && isChanged())
		{
			configElement.set(currentValue);

			return configElement.requiresMcRestart();
		}

		return false;
	}

	@Override
	public Integer getCurrentValue()
	{
		return currentValue;
	}

	@Override
	public Integer[] getCurrentValues()
	{
		return new Integer[] {getCurrentValue()};
	}
}