package telerik.entities.flying_objects;

import telerik.Constants;
import telerik.system.Position;
import telerik.system.Size;
import telerik.entities.OwnShip;
import telerik.entities.SmallExplosion;
import telerik.enumerators.BulletShipSide;
import telerik.game_states.PlayState;
import telerik.abstract_classes.FlyingObject;
import telerik.interfaces.Updatable;

public class FriendlyBullet extends FlyingObject implements Updatable {

    private int kind;
    private int power;
    private int speed;
    private int width;
    private int height;

    public FriendlyBullet(PlayState game, int kind, Position position, BulletShipSide side) {
        super(game);

        this.speed = Constants.FRIENDLY_BULLET_SPEED;
        this.kind = kind;
        OwnShip ship = game.getPlayer().getShip();

        if (kind == 1) {

            power = Constants.FRIENDLY_BULLET_1_POWER;
            width = Constants.FRIENDLY_BULLET_1_WIDTH;
            height = Constants.FRIENDLY_BULLET_1_HEIGHT;

            this.setImage(game.getSpriteSheet().getImage(650, 0, width, height));

        } else if (kind == 2) {

            power = Constants.FRIENDLY_BULLET_2_POWER;
            width = Constants.FRIENDLY_BULLET_2_WIDTH;
            height = Constants.FRIENDLY_BULLET_2_HEIGHT;

            this.setImage(game.getSpriteSheet().getImage(657, 0, width, height));

        }

        this.setSize(new Size(width, height));

        int x = this.getGame().getPlayer().getShip().getPosition().getX();
        int y = this.getGame().getPlayer().getShip().getPosition().getY() + 10;

        int shipWidth = this.getGame().getPlayer().getShip().getSize().getWidth();

        if (side == BulletShipSide.RIGHT) {

            this.setPosition(new Position(x + shipWidth - width, y));
        } else if (side == BulletShipSide.LEFT) {

            this.setPosition(new Position(x, y));
        } else if (side == BulletShipSide.MIDLEFT) {

            this.setPosition(new Position(x + shipWidth - width - 16, y + 5));
        } else {

            this.setPosition(new Position(x + 16, y + 5));
        }

        this.setBounds();

        ship.setBullets(ship.getBullets() - 1);
    }


    @Override
    public void update() {
        getPosition().setY(getPosition().getY() - speed);

        getBounds().moveBounds(this);

        if (getPosition().getY() <= 0) {
            getGame().getHandler().addToRemove(this);
        }
    }

    @Override
    public void onCollide() {
        getGame().getHandler().addToRemove(this);
        new SmallExplosion(getGame(), this);
    }

    public int getKind() {
        return kind;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Friendly bullet";
    }
}
