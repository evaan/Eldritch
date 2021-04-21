package com.evaan.frostburn.gui.button.buttons;

import com.evaan.frostburn.gui.button.ModuleButton;
import com.evaan.frostburn.gui.button.SettingButton;
import com.evaan.frostburn.module.Module;
import com.evaan.frostburn.util.Setting;
import com.evaan.frostburn.util.Wrapper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * @author Gopro336
 * @since 2/18/2021
 */
public class ModeButton extends SettingButton implements Wrapper
{
	private final Setting setting;

	public ModeButton(ModuleButton parent, Module module, Setting setting, double X, double Y, double W, double H, Boolean isSub)
	{
		super(parent, module, X, Y, W, H, isSub);
		this.setting = setting;
	}

	@Override
	public void render(MatrixStack matrices, int mX, int mY)
	{
		drawButton(matrices, mX, mY);

		textRenderer.draw(matrices, setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(30, 30, 30).getRGB());
		textRenderer.draw(matrices, setting.getValue().toString(), (float) ((getX() + getW() - 6) - textRenderer.getWidth(setting.getValue().toString())), (float) (getY() + 4), new Color(30, 30, 30).getRGB());
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		super.mouseClicked(mX, mY, mB);
		if (this.isHovering(mX, mY)) {
			String s = (setting.getValue() instanceof String ? (String) setting.getValue() : setting.getValue().toString());
			if (mB == 0) {
				try {
					if (!setting.getCorrectString(s).equalsIgnoreCase(setting.getOptions().get(setting.getOptions().size() - 1).toString())) {
						setting.setValue(setting.getOptions().get(setting.getOptions().indexOf(setting.getCorrectString(s)) + 1));
					} else {
						setting.setValue(setting.getOptions().get(0));
					}
				} catch (Exception e) {
					System.err.println("Mode Button Error");
					e.printStackTrace();
					setting.setValue(setting.getOptions().get(0));
				}
			}
			/*else if (mB == 1) {
			add decrement?
			}*/
		}
	}

	@Override
	public Setting getValue(){
		return setting;
	}

}
