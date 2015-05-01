package com.mygdx.game.enteties.guns;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.enteties.AbstractGameObject;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;

public class StandartGun  extends AbstractGun{

	public StandartGun(AbstractGameObject ob) {
		super(ob);
		super.setType(TypeOfBullet.Standrat);
		super.setInstance(10);
		setDelayTimer(0.5f);		
		setCenterGunX(ob.getWidth()/2);
		//Gdx.app.debug("ob.getWidth(): ", ob.getWidth() + "");
		setCenterGunY(ob.getHeight()/2);
	}

	public Bullet getBullet(){
		return creatBullet(TypeOfBullet.Standrat);
	}

	

}
