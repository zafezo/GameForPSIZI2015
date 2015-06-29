package com.mygdx.game.enteties.guns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enteties.AbstractGameObject;
import com.mygdx.game.util.AssetsStore;
import com.mygdx.game.util.Constants;

public class Bullet extends Sprite  {
	
	private final float speed = 85;
	private TypeOfBullet type;
	//private Sprite bullet;
	public Vector2 velocity;
	private float instance; 
	
	public enum TypeOfBullet {
		Standrat, Freeze, Poison;
				
	}
	
	public Bullet (float X, float Y, boolean directhion, 
			TypeOfBullet type, float instance) {
		super (createSprite(type));
		this.type = type;		
		//bullet = createSprite();	
		// Direction == false <-
		// Direction == true ->
		velocity =  !directhion ? new Vector2(-speed ,0): 
			new Vector2(speed ,0);
		//Gdx.app.debug("Bullet Speed: ", speed +"");
		//bullet.setPosition(X, Y);
		setPosition(X, Y);
		this.instance = instance;
		
	}
	
	public void setImage(String path){
		this.set(new Sprite(new Texture("img/piu/piu-"+path
				+".png")));
	}
	
	public void setInstance(float instance) {
		this.instance = instance;
	}
	
	public float getInstance() {
		return instance;
	}
	
	public TypeOfBullet getTypeOfBullet(){
		return type;
	}
	
	private static Sprite createSprite(TypeOfBullet type){
		Texture pixmap = null;
		switch (type){
		case Freeze:
			pixmap = AssetsStore.instance.eneties.frozenPiu;
			break;
		case Standrat:
			pixmap = AssetsStore.instance.eneties.standartPiu;
			break;
		default:
			break;
		}
		
		Sprite temp = new Sprite(pixmap);		
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
	public boolean isCollision(AbstractGameObject ob){
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
