package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Geometry;
import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.renderer.Sprite;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.awt.*;

/**
 * A Collider in form of a box for f.e. Player characters
 * @author Felix Kunze
 */
public class BoxCollider extends Quad implements Collidable{

    public BoxCollider(Sprite sprite, Vector2f posLB, float width, float height) {
        super(sprite, posLB, width, height);
    }

    public BoxCollider(Sprite sprite, Vector2f posLB, Vector2f posRB){
        super(sprite,posLB, posRB);
    }


    @Override
    public Rectangle toShape() {
        Rectangle r = new Rectangle(origin2Point());
        r.add(pos2Point(getVertices()[2].position));
        return r;
    }

    @Override
    public Point pos2Point(Vector2f glPoint){
        return new Point((int)(glPoint.x * getIntAccuracy()), (int)(glPoint.y * getIntAccuracy()));
    }

    public Point origin2Point() {
        return new Point((int)(getOrigin().x * getIntAccuracy()), (int)(getOrigin().y * getIntAccuracy()));
    }
}
