package com.mygdx.game.enteties.guns;

import com.mygdx.game.enteties.AbstractGameObject;
import com.mygdx.game.enteties.guns.Bullet.TypeOfBullet;

public class PoisonGun extends AbstractGun {

	public PoisonGun(AbstractGameObject ob) {
		super(ob);
		super.setType(TypeOfBullet.Poison);
		super.setInstance(2);
		setDelayTimer(0.5f);		
		setCenterGunX(ob.getWidth()/2);
		//Gdx.app.debug("ob.getWidth(): ", ob.getWidth() + "");
		setCenterGunY(ob.getHeight()/2);
	}

}
