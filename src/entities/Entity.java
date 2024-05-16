package entities;

import java.awt.*;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle hitBox;
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitBox();
    }
    protected void renderHitBox(Graphics g) { // for debugging
        g.setColor(Color.RED);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    private void initHitBox() {
        hitBox = new Rectangle((int)x, (int)y, width, height);
    }
    protected void updateHitBox() {
        hitBox.x = (int)x;
        hitBox.y = (int)y;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
}
