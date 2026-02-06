package name.gojean.meteor.translator;

import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.Tabs;
import meteordevelopment.meteorclient.settings.Settings;
import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.locale.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Translator {
    ArrayList<Translator> translators = new ArrayList<>();

    static void registerTranslators() {
        translators.add(() -> {
            try {
                for (Module module : Modules.get().getAll()) {
                    Reflect<Module> moduleRt = new Reflect<>(Module.class, module);
                    moduleRt.setValue("title", Language.getInstance().getOrDefault("meteor.module.title." + module.name, module.title));
                    moduleRt.setValue("description", Language.getInstance().getOrDefault("meteor.module.description." + module.name, module.description));
                }
            } catch (Exception e) {
                MeteorTranslator.LOG.error("Translator.registerTranslators FAILED when translate modules", e);
                throw new RuntimeException(e);
            }
        });

        translators.add(() -> {
            for (Tab tab : Tabs.get()) {
                try {
                    String tabName = tab.getClass().getSimpleName().toLowerCase();
                    new Reflect<>(Tab.class, tab).setValue("name", Language.getInstance().getOrDefault("meteor.tab." + tabName.substring(0, tabName.length() - 3), tab.name));
                } catch (Exception e) {
                    MeteorTranslator.LOG.error("Translator.registerTranslators FAILED when translate tabs", e);
                    throw new RuntimeException(e);
                }
            }
        });

        translators.add(() -> {
            try {
                @SuppressWarnings({"rawtypes", "unchecked"}) Map<Class<? extends System>, System<?>> systems =
                    (Map<Class<? extends System>, System<?>>) new Reflect<>(Systems.class, null).getValue("systems");
                //noinspection rawtypes
                for (Map.Entry<Class<? extends System>, System<?>> sysEntry : systems.entrySet()) {
                    @SuppressWarnings({"rawtypes", "unchecked"}) Reflect r = new Reflect(sysEntry.getKey(), sysEntry.getValue());
                    if (r.hasField("settings")) {
                        MeteorTranslator.translateSettings((Settings) r.getValue("settings"));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        translators.add(() -> {
            try {
                //noinspection unchecked
                for (GuiTheme theme : (List<GuiTheme>) new Reflect<>(GuiThemes.class, null).getValue("themes")) {
                    MeteorTranslator.translateSettings(theme.settings);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void translate();
}
