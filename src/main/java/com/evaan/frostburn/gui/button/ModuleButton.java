package com.evaan.frostburn.gui.button;

import com.evaan.frostburn.FrostBurn;
import com.evaan.frostburn.gui.Dropdown;
import com.evaan.frostburn.module.Module;
import com.evaan.frostburn.util.Wrapper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;

import java.awt.*;
//import java.util.ArrayList;

/**
 * @author Gopro336
 */

public class ModuleButton implements Wrapper
{
	public boolean startHighlight;
	private final Module module;
	//private final ArrayList<SettingButton> buttons = new ArrayList<>();
	private final double W;
	private final double H;
	private double X;
	private double Y;
	private boolean open;
	public Dropdown dropdown;

	public ModuleButton(Module module, double x, double y, double w, double h)
	{
		this.startHighlight = false;

		this.module = module;
		X = x;
		Y = y;
		W = w;
		H = h;

		dropdown = new Dropdown(this, this.getModule(), X, Y, W, H);
	}

	private final MatrixStack matrixStack = new MatrixStack();

	public void render(MatrixStack matrices, int mX, int mY)
	{
		FrostBurn.clickGUI.drawGradient(matrices, X, Y, X + W , Y + H, new Color(218, 218, 218, 232).getRGB(), new Color(218, 218, 218, 232).getRGB());

		if (module.isEnabled()) {
			Wrapper.textRenderer.draw(matrices, module.getName(), (float) (X + 5), (float) (Y + 4), new Color(30, 30, 216, 232).getRGB());
		}
		else {
			Wrapper.textRenderer.draw(matrices, module.getName(), (float) (X + 5), (float) (Y + 4), new Color(30, 30, 30).getRGB());
		}

		if (isHover(X, Y, W, H - 1, mX, mY)) {
			FrostBurn.clickGUI.drawGradient(matrices, X, Y, X + W , Y + H, new Color(140, 140, 140, 110).getRGB(), new Color(140, 140, 140, 110).getRGB());
		}

		if (open) {
			matrixStack.push();
			matrixStack.translate(X + H/2, Y + H/2, 0);
			rotate(90,0, 0,1);
			matrixStack.translate(-(X + H/2), -(Y + H/2), 0);
			textRenderer.draw(matrices, ("..."), (float) ((X + W - 3) - Wrapper.textRenderer.getWidth("...")), (float) (Y + 4), new Color(30, 30, 30).getRGB());
			matrixStack.pop();
		}
		else {
			textRenderer.draw(matrices, ("..."), (float) ((X + W - 3) - Wrapper.textRenderer.getWidth("...")), (float) (Y + 4), new Color(30, 30, 30).getRGB());
		}

	}

	public void rotate(float angle, float x, float y, float z) {
		matrixStack.multiply(new Quaternion(x * angle, y * angle, z * angle, true));
	}

	public void mouseDown(int mX, int mY, int mB) {
		if (!isHover(X, Y, W, H - 1, mX, mY)) return;

		if (mB == 0) {
			module.toggle();
			if (module.getName().equals("ClickGUI"))
				mc.openScreen(null);
		}
		else if (mB == 1) {
			processRightClick();
		}
	}

	private boolean isHover(double X, double Y, double W, double H, int mX, int mY) {
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}

	public void setX(double x) {
		X = x;
	}

	public void setY(double y) {
		Y = y;
	}

	public boolean isOpen() {
		return open;
	}

	public Module getModule() {
		return module;
	}

	/*public ArrayList<SettingButton> getButtons() {
		return buttons;
	}*/

	public void processRightClick() {
		open = !open;
	}
}
