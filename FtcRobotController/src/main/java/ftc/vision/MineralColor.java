package ftc.vision;

import org.opencv.core.Scalar;

/**
 * Created by Angela on 9/18/2018.
 */

public class MineralColor {

    private final Color leftColor;
    private final Color rightColor;

    public MineralColor(Color leftColor, Color rightColor) {
        this.leftColor = leftColor;
        this.rightColor = rightColor;
    }

    public Color getLeftColor() {
        return leftColor;
    }

    public Color getRightColor() {
        return rightColor;
    }

    @Override
    public String toString(){
        return rightColor + "," + leftColor;
    }

    public enum Color {
        RED (ImageUtil.RED),
        GREEN (ImageUtil.GREEN),
        BLUE (ImageUtil.BLUE),
        UNKNOWN (ImageUtil.BLACK);

        public final Scalar color;
        Color(Scalar color) {
            this.color = color;
        }

    }
}