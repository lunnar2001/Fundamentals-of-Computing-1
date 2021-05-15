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

package emily.util;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YTUtil {
    public final static Pattern yturl = Pattern.compile("^(?:https?:\\/\\/)?(?:(?:www\\.)?)?(?:youtube\\.com|youtu\\.be)\\/.*?(?:embed|e|v|watch.*?v=)?\\/?([-_a-z0-9]{10,})?(?:&?index=\\d+)?(?>(?:playlist\\?|&)?list=([^#\\\\&\\?]{12,}))?", Pattern.CASE_INSENSITIVE);
    private final static Pattern youtubeCode = Pattern.compile("^[A-Za-z0-9_-]{11}$");

    /**
     * checks if it could be a youtube videocode
     *
     * @param videocode code to check
     * @return could be a code
     */
    public static boolean isValidYoutubeCode(String videocode) {
        return youtubeCode.matcher(videocode).matches();
    }

    /**
     * Extracts the videocode from an url
     *
     * @param url youtube link
     * @return videocode
     */
    public static String extractCodeFromUrl(String url) {
        Matcher matcher = yturl.matcher(url);
        if (matcher.find()) {
            if (matcher.group(1) != null) {
                return matcher.group(1);
            }
        }
        return url;
    }

    /**
     * Extracts the playlistcode from a yt url
     *
     * @param url the url
     * @return playlistcode || null if not found
     */
    public static String getPlayListCode(String url) {
        Matcher matcher = yturl.matcher(url);
        if (matcher.find()) {
            if (matcher.groupCount() == 2) {
                return matcher.group(2);
            }
        }
        return null;
    }

    /**
     * @param videocode youtubecode
     * @return whats in the <title> tag on a youtube page
     */
    public static String getTitleFromPage(String videocode) {
        String ret = "";
        try {
            URL loginurl = new URL("https://www.youtube.com/watch?v=" + videocode);
            URLConnection yc = loginurl.openConnection();
            yc.setConnectTimeout(10 * 1000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            StringBuilder input = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                input.append(inputLine);
            in.close();
            int start = input.indexOf("<title>");
            int end = input.indexOf("</title>");
            ret = input.substring(start + 7, end - 10);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return StringEscapeUtils.unescapeHtml4(ret);
    }

    /**
     * Time until the next google api reset happens (Midnight PT), or 9am GMT
     *
     * @return formatted string, eg. "10 minutes form now"
     */
    public static String nextApiResetTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return TimeUtil.getRelativeTime(
                (System.currentTimeMillis() +
                        (c.getTimeInMillis() - System.currentTimeMillis()) % TimeUnit.DAYS.toMillis(1)) / 1000L, false);
    }
}
