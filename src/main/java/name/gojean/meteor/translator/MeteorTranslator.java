package name.gojean.meteor.translator;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.Settings;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import net.minecraft.locale.Language;
import org.slf4j.Logger;

public class MeteorTranslator extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    public static void translateSettings(Settings settings) throws NoSuchFieldException, IllegalAccessException {
        for (SettingGroup sg : settings) {
            //noinspection rawtypes
            for (Setting s : sg) {
//                MeteorTranslator.LOG.info("\"meteor.setting.title.{}\": \"{}\"", s.name, s.title);
//                MeteorTranslator.LOG.info("\"meteor.setting.description.{}\": \"{}\"", s.name, s.description);
                @SuppressWarnings("rawtypes")
                Reflect<Setting> settingRt = new Reflect<>(Setting.class, s);
                settingRt.setValue("title", Language.getInstance().getOrDefault("meteor.setting.title." + s.name, s.title));
                settingRt.setValue("description", Language.getInstance().getOrDefault("meteor.setting.description." + s.name, s.description));
            }
        }
    }

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Addon Template");
        Translator.registerTranslators();
    }

    @Override
    public String getPackage() {
        return "name.gojean.meteor.translator";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("MeteorDevelopment", "meteor-translator");
    }
}
