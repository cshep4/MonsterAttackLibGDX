package com.cshep4.monsterattack.game.ai;

import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;

import java.util.List;

public interface ProducerAI extends Enemy {
	void decisionTree(List<RunningEnemy> enemies);
}
