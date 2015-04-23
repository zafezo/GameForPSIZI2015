package com.mygdx.game.enteties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.Constants;
import com.ygdx.game.world.WorldController;

public class Bullet extends Sprite{
	
	private final float speed = 60;
	private final static int size = 2;
	
	public TypeOfBullet type;
	//private Sprite bullet;
	public Vector2 velocity;
	
	public enum TypeOfBullet {
		Standrat, Freeze
	}
	
	public Bullet (float X, float Y, boolean directhion, TypeOfBullet type) {
		super (createSprite());
		this.type = type;		
		//bullet = createSprite();	
		// Direction == true ->
		// Direction == false <-
		velocity =  directhion ? new Vector2(speed ,0): 
			 new Vector2(-speed ,0);
		//bullet.setPosition(X, Y);
		setPosition(X, Y);
	}
	
	private static Sprite createSprite(){
		Pixmap pixmap = new Pixmap(size,size, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		Sprite temp = new Sprite(new Texture(pixmap));		
		return temp;		
	}
	
	public void update (float deltaTime){		
			setX(getX() + velocity.x * deltaTime);
	}
	
	public void render(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		draw(batch);
	}
	
	
	public boolean isCollision(){
			if(getX() < Constants.mapTiledWidth |
					getX() > (Constants.mapWidth - 1)*Constants.mapTiledWidth)
				return true;		
		return false;
	}
	public boolean isCollision(Sprite ob){
		Rectangle recOb = ob.getBoundingRectangle();
		Rectangle recBullet = getBoundingRectangle();
		return recOb.overlaps(recBullet);			
	}
	
	public boolean isCollision (TiledMapTileLayer collisionLayer){
			Cell cell = collisionLayer.getCell((int) (getX() / collisionLayer.getTileWidth()), 
					(int) (getY() / collisionLayer.getTileHeight()));
			return cell != null && cell.getTile() != null && cell.getTile()
					.getProperties().containsKey("blocked");
	}
	

}
