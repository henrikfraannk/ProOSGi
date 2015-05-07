package dk.sdu.mmmi.cbse.enemy;

import com.decouplink.Context;
import static com.decouplink.Utilities.context;
import dk.sdu.mmmi.cbse.common.data.BehaviourEnum;
import static dk.sdu.mmmi.cbse.common.data.BehaviourEnum.HIT;
import static dk.sdu.mmmi.cbse.common.data.BehaviourEnum.MOVE_RANDOM;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityType;
import static dk.sdu.mmmi.cbse.common.data.EntityType.ENEMY;
import dk.sdu.mmmi.cbse.common.data.Health;
import dk.sdu.mmmi.cbse.common.data.Rotation;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static dk.sdu.mmmi.cbse.common.utils.EntityFactoryUtil.createBullet;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class EnemyProcessingService implements IEntityProcessingService {

    private int turnDirection = 1;

    @Override
    public void process(Object world, Entity entity) {

        Context entityCtx = context(entity);

        if (entityCtx.one(EntityType.class).equals(ENEMY)) {

            for (BehaviourEnum behaviour : entityCtx.all(BehaviourEnum.class)) {

                if (behaviour.equals(MOVE_RANDOM)) {

                    // Get context from entity
                    Rotation rotation = entityCtx.one(Rotation.class);

                    // Generate random movement direction
                    if (Math.random() < 0.05) {
                        turnDirection = -turnDirection;
                    }

                    rotation.angle += turnDirection * 0.05;

                    // Fire
                    if (Math.random() < 0.02) {
                        Entity e = createBullet(entity);
                        context(world).add(Entity.class, e);
                    }
                }

                if (behaviour.equals(HIT)) {

                    Health h = entityCtx.one(Health.class);

                    // Damage
                    h.addDamage(1);

                    // Check for destroyed
                    if (!h.isAlive()) {
                        entity.setDestroyed(true);
                    }

                    // Remove hit behaviour
                    context(entity).remove(behaviour);
                }
            }
        }
    }
}
