package io.finefabric.ninebanana.achievements;

/**
 * Adapter for listener
 */
abstract class AchievementListenerAdapter implements AchievementListener {
    @Override
    public void onAchievementDismissed(AchievementUnlocked achievement) {
    }

    @Override
    public void onViewCreated(AchievementUnlocked achievement, AchievementData[] data) {
    }

    @Override
    public void onAchievementMorphed(AchievementUnlocked achievement, AchievementData data) {
    }
}
