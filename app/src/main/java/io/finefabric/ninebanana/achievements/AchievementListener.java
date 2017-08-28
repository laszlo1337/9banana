package io.finefabric.ninebanana.achievements;

/**
 * Created by laszlo on 2017-08-28.
 */ /* used by the abstract class adapter */
interface AchievementListener {
    void onViewCreated(AchievementUnlocked achievement, AchievementData[] data);

    void onAchievementMorphed(AchievementUnlocked achievement, AchievementData data);

    void onAchievementDismissed(AchievementUnlocked achievement);
}
