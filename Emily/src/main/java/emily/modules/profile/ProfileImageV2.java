/*
 * Copyright 2017 github.com/kaaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emily.modules.profile;

import emily.main.Launcher;
import emily.util.GfxUtil;
import net.dv8tion.jda.core.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created on 28-9-2016
 */
public class ProfileImageV2 extends ProfileImage {
    private Random rng;

    public ProfileImageV2(User user) {
        super(user);
        rng = new Random();
    }

    public File getProfileImage() throws IOException {
        int fontsize = 28;
        if (getUser().getName().length() <= 4) {
            fontsize = 32;
        }
        if (getUser().getName().length() > 8) {
            fontsize = 22;
        }
        Font defaultFont = new Font("Forte", Font.BOLD + Font.ITALIC, fontsize);
        Font score = new Font("Forte", Font.BOLD, 24);
        Font creditFont = new Font("Forte", Font.ITALIC, 12);
        BufferedImage result = new BufferedImage(
                320, 265,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        BufferedImage profileImg = getUserAvatar();
        BufferedImage backgroundImage = ImageIO.read(Launcher.class.getClassLoader().getResource("profile_bg_5.png"));

        g.drawImage(profileImg, 18, 33, 141, 159, 0, 0, profileImg.getWidth(), profileImg.getHeight(), null);
        g.drawImage(backgroundImage, 0, 0, 320, 265, 0, 0, 320, 265, null);

        GfxUtil.addCenterShadow(getUser().getName(), defaultFont, 222, 71 + (fontsize / 2), g, Color.black);
        GfxUtil.addCenterText(getUser().getName(), defaultFont, 222, 71 + (fontsize / 2), g, Color.white);
        GfxUtil.addRightText("made by Emily", creditFont, 318, 199, g, new Color(0x3A3A38));

        GfxUtil.addText("" + rng.nextInt(1000), score, 173, 118, g, new Color(0xffff00));//rewards
        GfxUtil.addRightText("" + rng.nextInt(1000), score, 290, 118, g, new Color(0x36cbe9));//currency

        GfxUtil.addCenterText("" + rng.nextInt(100), score, 31, 246, g, new Color(0x5c7e32));//health
        GfxUtil.addCenterText("" + rng.nextInt(100), score, 134, 246, g, new Color(0x5c7e32));//attack
        GfxUtil.addCenterText("" + rng.nextInt(100), score, 237, 246, g, new Color(0x5c7e32));//defense
        File file = new File("profile_v2_" + getUser().getId() + ".png");
        ImageIO.write(result, "png", file);
        return file;
    }
}
