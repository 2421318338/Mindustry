package mindustry.type;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.ArcAnnotate.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.ctype.*;
import mindustry.entities.type.base.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;

//TODO change to UnitType or Shell or something
public class UnitDef extends UnlockableContent{
    //TODO implement
    public @NonNull Prov<? extends Unitc> constructor = () -> this.flying ? new FlyingUnit() : new GroundUnit();
    public TypeID typeID;

    public boolean flying;
    public float speed = 1.1f, boostSpeed = 0.75f, rotateSpeed = 0.2f, baseRotateSpeed = 0.1f;
    public float drag = 0.3f, mass = 1f, accel = 0.1f;
    public float health = 200f, range = -1;
    public boolean targetAir = false, targetGround = false;
    public boolean faceTarget = true; //equivalent to turnCursor

    public int itemCapacity = 30;
    public int drillTier = -1;
    public float buildPower = 1f, minePower = 1f;

    public Color engineColor = Pal.boostTo;
    public float engineOffset = 5f, engineSize = 2.5f;

    public float hitsize = 6f, hitsizeTile = 4f;
    public float cellOffsetX = 0f, cellOffsetY = 0f;
    public float lightRadius = 60f;
    public Color lightColor = Pal.powerLight;
    public boolean drawCell = true, drawItems = true;

    public ObjectSet<StatusEffect> immunities = new ObjectSet<>();
    public Sound deathSound = Sounds.bang;

    public Array<Weapon> weapons = new Array<>();
    public TextureRegion baseRegion, legRegion, region, cellRegion;

    public UnitDef(String name){
        super(name);

        //TODO replace with the sane constructor
        typeID = new TypeID(name, constructor);
    }

    public Unitc create(Team team){
        Unitc unit = constructor.get();
        unit.init(this, team);
        return unit;
    }

    @Override
    public void displayInfo(Table table){
        ContentDisplay.displayUnit(table, this);
    }

    @CallSuper
    @Override
    public void init(){
        //set up default range
        if(range < 0){
            for(Weapon weapon : weapons){
                range = Math.max(range, weapon.bullet.range());
            }
        }
    }

    @CallSuper
    @Override
    public void load(){
        weapons.each(Weapon::load);
        region = Core.atlas.find(name);
        legRegion = Core.atlas.find(name + "-leg");
        baseRegion = Core.atlas.find(name + "-base");
        cellRegion = Core.atlas.find(name + "-cell", Core.atlas.find("power-cell"));
    }

    @Override
    public ContentType getContentType(){
        return ContentType.unit;
    }

    //TODO remove methods below!

    public void update(Unitc player){
    }

    public void draw(Unitc player){
    }

    public void drawStats(Unitc player){
        if(drawCell){
            float health = player.healthf();
            Draw.color(Color.black, player.team().color, health + Mathf.absin(Time.time(), health * 5f, 1f - health));
            Draw.rect(player.getPowerCellRegion(),
            player.x + Angles.trnsx(player.rotation, cellOffsetY, cellOffsetX),
            player.y + Angles.trnsy(player.rotation, cellOffsetY, cellOffsetX),
            player.rotation - 90);
            Draw.reset();
        }

        if(drawItems){
            //player.drawBackItems(0f, true);
        }
    }

    public float getExtraArmor(Unitc player){
        return 0f;
    }

    //TODO remove
    public float spreadX(Unitc player){
        return 0f;
    }

    //TODO remove
    public float getRotationAlpha(Unitc player){
        return 1f;
    }

    public boolean canShoot(Unitc player){
        return true;
    }

    public void onLand(Unitc player){
    }

}
