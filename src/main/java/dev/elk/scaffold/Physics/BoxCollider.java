package dev.elk.scaffold.Physics;

import dev.elk.scaffold.gl.Quad;
import dev.elk.scaffold.gl.Vertex;
import dev.elk.scaffold.renderer.Sprite;
import dev.elk.scaffold.util.Utils;
import org.joml.Vector2f;

import java.awt.*;

/**
 * A Collider in form of a box for f.e. Player characters
 * @author Felix Kunze
 */
public class BoxCollider extends Quad implements Collidable{


    public BoxCollider(Sprite sprite, Vector2f posLB, float width, float height) {
        super(sprite, posLB, width, height);
        COLLIDABLES.add(this);
    }

    public BoxCollider(Sprite sprite, Vector2f posLB, Vector2f posRB){
        super(sprite,posLB, posRB);
        COLLIDABLES.add(this);
    }


    @Override
    public Rectangle toShape() {
        Rectangle r = new Rectangle(Utils.pos2Point(getOrigin()));
        r.add(Utils.pos2Point(getVertices()[2].position));
        return r;
    }

    @Override
    public void translate(Vector2f vector) {
        Rectangle r = toShape();
        Point scalar = Utils.pos2Point(vector);
        r.translate(scalar.x, scalar.y);
        COLLIDABLES.remove(this);
        for (Collidable collidable : COLLIDABLES)
            if (Utils.collides(r, collidable.toShape() )) {
                Rectangle rectangle = r.createIntersection(collidable.toShape().getBounds()).getBounds();
                vector = Utils.point2Pos(new Point(0,rectangle.height ));
            }

        COLLIDABLES.add(this);
        super.translate(vector);
    }
}
