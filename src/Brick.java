/**
 * Created by Fantomasa on 19.4.2016 г..
 */

import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Brick extends Sprite {
    private boolean destroyed;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon("src/img/Brick.png");
        this.image = ii.getImage();
        this.i_width = this.image.getWidth((ImageObserver)null);
        this.i_heigth = this.image.getHeight((ImageObserver)null);
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void setDestroyed(boolean val) {
        this.destroyed = val;
    }
}

