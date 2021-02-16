package com.rich.diy

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.inject.Context
import ktx.inject.register
import ktx.scene2d.Scene2DSkin
import ktx.style.*

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Main : KtxGame<Screen>() {
    val context = Context()

    override fun create() {
//        enableKtxCoroutines(asynchronousExecutorConcurrencyLevel = 1)
        context.register {
            bindSingleton(TextureAtlas("skin.atlas"))
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton<Viewport>(ScreenViewport())
            bindSingleton(Stage(inject(), inject()))
            bindSingleton(createSkin(inject()))
            Scene2DSkin.defaultSkin = inject()
            bindSingleton(this@Main)
            bindSingleton(FirstScreen(inject()))
        }

        addScreen(context.inject<FirstScreen>())
        setScreen<FirstScreen>()
    }

    fun createSkin(atlas: TextureAtlas): Skin = skin(atlas) { skin ->
        add(defaultStyle, BitmapFont())
        add(
            "decorative", FreeTypeFontGenerator("decorative.ttf".toInternalFile())
                .generateFont(FreeTypeFontGenerator.FreeTypeFontParameter().apply {
                    borderWidth = 2f
                    borderColor = Color.GRAY
                    size = 50
                })
        )
        label {
            font = skin[defaultStyle]
        }
        label("decorative") {
            font = skin["decorative"]
        }
        textButton("decorative") {
            font = skin["decorative"]
            overFontColor = Color.GRAY
            downFontColor = Color.DARK_GRAY
        }
        window {
            titleFont = skin[defaultStyle]
            stageBackground = skin["black-alpha"]
        }
    }

    companion object {
        
    }
}