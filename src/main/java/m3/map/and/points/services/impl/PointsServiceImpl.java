package m3.map.and.points.services.impl;

import lombok.RequiredArgsConstructor;
import m3.lib.settings.MapSettings;
import m3.map.and.points.data.PointData;
import m3.map.and.points.enums.DataObjects;
import m3.map.and.points.services.PointsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

    private final List<PointData> points = List.of(


            new PointData(1L, 10L, 100L, 200L, 300L,
                    Map.of(DataObjects.OBJECT_BLOCK, 12),
                    List.of("     ",
                            "     ",
                            "     ",

                            "□□□□□",
                            "□□□□□",
                            "□□□□□",
                            "□□□□□"),
                    List.of(
                            "RRGRR",
                            "GBRBG",
                            "RGBRG",

                            "YRPRR",
                            "BYBPB",
                            "RPRBR",
                            "YRBRB"
                    ),
                    null
            ),

            new PointData(2L, 15L, 100L, 200L, 300L,

                    Map.of(DataObjects.OBJECT_PURPLE, 12),
                    List.of(
                            "        ",
                            "        ",
                            "        ",
                            "        ",
                            "        ",

                            " □□□□□□ ",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            " □□□□□□ "), List.of(              /** WizardLevel_2 lightning */
                    "BRGPPBGR",
                    "RGBPPRGP",
                    "RGBBRRGP",
                    "GPYPPYPP",
                    "RGRPPRGB",

                    "RGBPPRGB",
                    "PRPPRPRG",
                    "RPPBGPGR",
                    "GPYPGYPG",
                    "BYPGPPBB"), null),


            new PointData(3L, 8L, 100L, 200L, 600L, Map.of(DataObjects.OBJECT_GREEN, 12),
                    List.of(
                            "         ",
                            "         ",
                            "         ",
                            "         ",
                            "         ",
                            "  □□□□□  ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"

                    ), List.of(  /** WizardLevel_3 polycolor gem */
                    "RBYYYPPBB",
                    "GPRGGGGRG",
                    "BRGYRBGGB",
                    "GGGGGGGGG",

                    "GGRBGRPGG",
                    "BRGYRBBGB",
                    "GPRBGGPRG",
                    "GRPPBPPGR",
                    "BGBYPGYPG",
                    "RBYYGYPBB"), null),

            new PointData(4L, 18L, 600L, 1500L, 2000L,

                    Map.of(DataObjects.OBJECT_GOLD, 4),

                    List.of(
                            "",
                            "",
                            "",
                            "",
                            "",

                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□"

                    ), List.of(   /** WizardLevel_4 gold*/
                    "BRGYRBBGB",
                    "GPRBGGPRG",
                    "GRPPBPPGR",
                    "BGBYPGYPG",
                    "RBYYGYPBB",

                    "RBYYGPPBB",
                    "PPRPRGPRG",
                    "BRGYRBBGB",
                    "RBYYPPPBB",
                    "GPRBGRPRG"),
                    List.of(List.of(
                            "",
                            "",
                            "",
                            "",
                            "",

                            "      ",
                            " $  $ ",
                            "   ⭥  ",
                            " $  $ ",
                            "      "))),


            new PointData(5L, 24L, 1000L, 1500L, 8000L,

                    Map.of(DataObjects.OBJECT_GOLD, 17 - 4),

                    List.of(
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□"
                    ), null, List.of(List.of(
                    "       ",
                    " $ $ $ ",
                    "   $   ",
                    " $$$$$ ",
                    "   $   ",
                    " $ $ $ ",
                    "       "))),

            new PointData(6L, 21L, 600L, 1000L, 2000L,

                    Map.of(DataObjects.OBJECT_GOLD, 6),
                    List.of(
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□"
                    ), null,
                    List.of(List.of(
                            "      ",
                            " $  $ ",
                            "      ",
                            " $  $ ",
                            "      ",
                            " $  $ ",
                            "      "))),

            new PointData(7L, 28L, 100L, 3000L, 5000L,

                    Map.of(DataObjects.OBJECT_GOLD, 9,
                            DataObjects.OBJECT_RED, 33),
                    List.of(
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□"
                    ), null,
                    List.of(List.of(
                            "        ",
                            "        ",
                            "   $    ",
                            "   $    ",
                            " $$$$$  ",
                            "   $    ",
                            "   $    ",
                            "        "))),


            new PointData(8L, 24L, 100L, 1000L, 5000L,

                    Map.of(DataObjects.OBJECT_GOLD, 12,
                            DataObjects.OBJECT_RED, 10,
                            DataObjects.OBJECT_GREEN, 10),
                    List.of(
                            "  □□□□  ",
                            " □□□□□□ ",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "  □□□□  ",
                            "  □□□□  "
                    ), null, List.of(List.of(
                    "       ",
                    "  $$$$ ",
                    "  $  $ ",
                    "  $  $ ",
                    "  $$$$ ",
                    "       ",
                    "       "))),

            new PointData(9L, 25L, 1000L, 2000L, 3500L,

                    Map.of(DataObjects.OBJECT_GOLD, 8),
                    List.of(
                            "",
                            "",
                            "",
                            "",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□"),
                    List.of(  /** WizardLevel9_1 Взорви камни рядом с ящиком.*/
                            "RGBYPG",
                            "GBRPYP",

                            "PPGRBP",
                            "YGYGYP",
                            "GBRBRB",
                            "RRPRYP",
                            "BGRYPR",
                            "BPPYRP",
                            "GBRBRB",
                            "GPBRPB"),
                    List.of(List.of(
                                    "",
                                    "",
                                    "",

                                    "",
                                    "",
                                    "",
                                    "■■■■■■",
                                    "■■■■■■",
                                    "",
                                    "")
                            , List.of(
                                    "",
                                    "",
                                    "",

                                    "",
                                    "",
                                    "",
                                    "$$  $$",
                                    " $$$$ ",
                                    "",
                                    ""))),

            new PointData(10L, 20L, 1000L, 2000L, 2500L,

                    Map.of(DataObjects.OBJECT_GOLD, 4,
                            DataObjects.OBJECT_RED, 9),
                    List.of(
                            "□     □ ",
                            "□     □ ",
                            "□□□□□□□ ",
                            "□□□□□□□ ",
                            "□ □□□ □ ",
                            "□ □□□ □ ",
                            "□ □□□ □ ",
                            "□□□□□□□ "
                    ), null, List.of(
                    List.of("",
                            "",
                            "",
                            "  ■ ■ ",
                            "  ■■■ ",
                            "   ■",
                            "   ■"
                    ), List.of(
                            "",
                            "",
                            "",
                            "$ $ $ $",
                            " ",
                            "",
                            ""
                    ))),

            new PointData(11L, 45L, 500L, 1500L, 2000L,

                    Map.of(DataObjects.OBJECT_BLUE, 40,
                            DataObjects.OBJECT_RED, 40,
                            DataObjects.OBJECT_YELLOW, 40),
                    List.of(
                            "        ",
                            "        ",

                            "□□□□□□□ ",
                            "□     □ ",
                            "□□□□□□□ ",
                            "□□□□□□□ ",
                            "□□□□□□□ ",
                            "□□□□□□□ ",
                            "□     □ ",
                            "□□□□□□□ "), null, List.of(List.of(
                    "",
                    "",

                    "",
                    "",
                    " ■■■■■ ",
                    "",
                    "",
                    " ■■■■■ ",
                    "",
                    ""))),

            new PointData(12L, 15L, 100L, 500L, 800L,

                    Map.of(DataObjects.OBJECT_BETA, 4),
                    List.of(
                            "",
                            "",

                            "□□□□□",
                            "□□□□□",
                            "□□□□□",
                            "□□□□□",
                            "□□□□□"),
                    /** WizardLevel_12 Собирай пауков, взрывая камни на которых они сидят. */
                    List.of(
                            "GBRPYPY",
                            "GPBRPBG",

                            "PPBRBPY",
                            "RRPRYPB",
                            "YPYGYYG",
                            "BRPYRPB",
                            "GBRBRBR",
                            "BGRYPRR"),
                    List.of(List.of(
                            "",
                            "",

                            "",
                            " β β ",
                            "     ",
                            " β β ",
                            "",
                            ""
                    ))),

            new PointData(13L, 15L, 100L, 1000L, 1500L,

                    Map.of(DataObjects.OBJECT_BETA, 8),
                    List.of(
                            "□ □ □ □",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□ □□□"
                    ), null, List.of(List.of(
                    "β β β β",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "β β β β"))),
            new PointData(14L, 15L, 500L, 1000L, 1500L,

                    Map.of(DataObjects.OBJECT_BETA, 8,
                            DataObjects.OBJECT_GOLD, 8),
                    List.of(
                            "",
                            "",

                            "  □□□  ",
                            "□□□□□□□",
                            " □□□□□ ",
                            " □□□□□ ",
                            "□□□□□□□",
                            "□□□□□□□",
                            "  □□□  "
                    ), null, List.of(List.of(
                    "",
                    "",

                    "  βββ  ",
                    "       ",
                    "  βββ  ",
                    "       ",
                    "       ",
                    "  β β  ",
                    "       "
            ), List.of(
                    "",
                    "",

                    "       ",
                    "       ",
                    "  ■■■  ",
                    "       ",
                    "       ",
                    "  ■■■  ",
                    "       "
            ), List.of(
                    "",
                    "",

                    "       ",
                    "  $ $  ",
                    "  $$$  ",
                    "       ",
                    "       ",
                    "  $$$  ",
                    "       "))),

            new PointData(15L, 30L, 1000L, 2000L, 35000L,

                    Map.of(DataObjects.OBJECT_BETA, 5,
                            DataObjects.OBJECT_RED, 12,
                            DataObjects.OBJECT_GREEN, 12),
                    List.of(
                            "  □ □ □ ",
                            "□ □□□□□ □",
                            "□ □□□□□ □",
                            "□□□□□□□□□ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□ ",
                            "□□□□□□□□□"), List.of(  /** WizardLevel_15 Собери кристаллы, чтобы снять цепь.*/
                    "GRGYGPGRG",
                    "BGRRPRRGG",
                    "YPYGPYBBR",
                    "GGPRYPPRP",
                    "PPBPBPPBP",
                    "GBGBGBGBG",
                    "GBPRYPBYP"),
                    List.of(List.of(
                            "  β β β  ",
                            "β       β",
                            "         ",
                            "bbbbbbbbb",
                            "b       b",
                            "b       b",
                            "b       b",
                            "b       b",
                            "bbbbbbbbb"
                    ), List.of(
                            "",
                            "",
                            "",
                            "╱  XXX  ╲",
                            "",
                            "  ╱╲ ╱╲",
                            ""))),
            new PointData(16L, 15L, 500L, 1000L, 3500L,

                    Map.of(DataObjects.OBJECT_GOLD, 4,
                            DataObjects.OBJECT_BETA, 4),
                    List.of(
                            " □□□□□□ ",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□  □□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□"), null, List.of(List.of(
                    "       ",
                    "β β  β β",
                    " $    $ ",
                    "       ",
                    " $    $ ",
                    "       ",
                    "       "))),

            new PointData(17L, 35L, 500L, 2500L, 5500L,

                    Map.of(DataObjects.OBJECT_GOLD, 4,
                            DataObjects.OBJECT_BETA, 4,
                            DataObjects.OBJECT_BOX, 3),
                    List.of(
                            "  □□□□□  ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□ □□□□",
                            "□□□□ □□□□",
                            "□□□□ □□□□")

                    , null, List.of(List.of(
                    "         ",
                    "  βbbbβ  ",
                    "   β β   ",
                    "         ",
                    "         ",
                    "         ",
                    " $$   $$ "

            ), List.of(
                    "         ",
                    "╱╲╱╲X╱╲╱╲",
                    "╲       ╱",
                    "         ",
                    "         ",
                    "        "))),

            new PointData(18L, 15L, 500L, 1000L, 4000L,

                    Map.of(
                            DataObjects.OBJECT_GOLD, 8,
                            DataObjects.OBJECT_BOX, 8),
                    List.of(
                            "",
                            "",

                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "  □□□  ",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□"), null,/*gems__: [
            "△⨀▭◆⨀▭△",
            "△▭◆▭◇⨀◇",

            "△▭△▭△▭△",
            "△◆△◇△⨀△",
            "▭△◆◇⨀◆◆",
            "△◆⨀◆◇⨀△",
            "◇◆⨀▭◇⨀⨀",
            "⨀⨀▭◆▭⨀◇",
            "◇⨀◇△⨀◇▭"), */
                    List.of(List.of(
                            "",
                            "",

                            "  ■■■  ",
                            "  ■ ■  ",
                            "       ",
                            "  ■■■  ",
                            "       ",
                            "       ",
                            "       "
                    ), List.of(
                            "",
                            "",

                            "  $$$  ",
                            "  $ $  ",
                            "       ",
                            "  $$$  ",
                            "       ",
                            "       ",
                            "       "
                    ))),
            new PointData(19L, 40L, 7000L, 7500L, 8000L,

                    Map.of(DataObjects.OBJECT_GOLD, 20),
                    List.of(
                            "□□□□□□□",
                            " □□□□□ ",
                            "□□□□□□□",
                            "□□□□□□□ ",
                            "□□□□□□□",
                            "□□□□□□□ ",
                            "□□□□□□□",
                            "□□□□□□□"), null, List.of(List.of(
                    "       ",
                    "       ",
                    "       ",
                    "bbbbbbb",
                    "b     b",
                    "b     b",
                    "b     b",
                    "bbbbbbb"
            ), List.of(
                    "       ",
                    "       ",
                    "       ",
                    "$$$$$$$",
                    "$     $",
                    "$     $",
                    "$     $",
                    "$$$$$$$"
            ), List.of(
                    "       ",
                    "       ",
                    "       ",
                    "       ",
                    " X   X ",
                    "       ",
                    " X   X ",
                    "       "))),

            new PointData(20L, 15L, 200L, 400L, 700L,

                    Map.of(DataObjects.OBJECT_BETA, 5),
                    List.of(
                            "",
                            "",

                            "",
                            "",
                            "□□□□□",
                            "□□□□□",
                            "□□□□□",
                            "□□□□□",
                            ""
                    ), null, List.of(List.of(
                    "",
                    "",

                    "      ",
                    "      ",
                    "      ",
                    " β β  ",
                    " β β  ",
                    "  β   ",
                    "      "
            ), List.of(
                    "",
                    "",

                    "",
                    "",
                    "",
                    " ╲ ╱  ",
                    " ╱ ╲  ",
                    "",
                    ""))),

            new PointData(21L, 35L, 500L, 1000L, 2000L,

                    Map.of(DataObjects.OBJECT_BETA, 5,
                            DataObjects.OBJECT_GOLD, 8),
                    List.of(
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□"
                    ), null, List.of(List.of(
                    "        ",
                    " β   β  ",
                    "        ",
                    "   β    ",
                    "        ",
                    " β   β  ",
                    "        "
            ), List.of(
                    "   $    ",
                    "        ",
                    " $   $  ",
                    "  $ $   ",
                    " $   $  ",
                    "        ",
                    "   $    "
            ), List.of(
                    "        ",
                    " ╲   ╱  ",
                    "        ",
                    "   X    ",
                    "        ",
                    " ╱   ╲  ",
                    "        "))),

            new PointData(22L, 25L, 500L, 1000L, 1500L,

                    Map.of(DataObjects.OBJECT_BETA, 7,
                            DataObjects.OBJECT_BOX, 15),
                    List.of(
                            " □□ □□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            "□□□□□□□",
                            " □□ □□"
                    ), null, List.of(List.of(
                    " ββ ββ ",
                    "β  β  β",
                    "       ",
                    "       ",
                    "       ",
                    "       ",
                    "       "
            ), List.of(
                    "       ",
                    "       ",
                    " ■■■■■",
                    " ■■■■■",
                    " ■■■■■",
                    "       ",
                    "       "
            ), List.of(
                    "       ",
                    "X  X  X",
                    "       ",
                    "       ",
                    "       ",
                    "       ",
                    "       "))),

            new PointData(23L, 15L, 500L, 1000L, 1500L,

                    Map.of(DataObjects.OBJECT_ALPHA, 4),

                    List.of(
                            " □□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            " □□□□□□□ "

                    ), List.of(            // WIZARD
                    "RGBBPYRPR",
                    "GRRGRBPYR",
                    "BBαRGPαPR",
                    "PRGBBRYRP",
                    "YPαRBGαBY",
                    "PPPYPRRGB"
            ), List.of(List.of(
                    "         ",
                    "         ",
                    "         ",
                    "■■■■■■■■■",
                    "         ",
                    "         "
            ))),

            new PointData(24L, 20L, 500L, 1200L, 2500L,

                    Map.of(DataObjects.OBJECT_RED, 20),
                    List.of(
                            "□□□□ □□□□",
                            "□□□□ □□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□ □□□□",
                            "□□□□ □□□□"), null, null),

            new PointData(25L, 35L, 500L, 1500L, 5000L,

                    Map.of(DataObjects.OBJECT_YELLOW, 20,
                            DataObjects.OBJECT_PURPLE, 20,
                            DataObjects.OBJECT_BETA, 18),
                    List.of(
                            "□□□   □□□",
                            "□□□   □□□",
                            "□□□□ □□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(
                    "ββ     ββ",
                    "         ",
                    "β       β",
                    "ββββ ββββ",
                    "         ",
                    "  ββ ββ  ",
                    "■■■■■■■■■  "
            ), List.of(
                    "",
                    "",
                    "",
                    "╱╲╱  ╲╱╲",
                    "",
                    "",
                    ""))),

            new PointData(26L, 30L, 500L, 5500L, 6000L,

                    Map.of(DataObjects.OBJECT_BETA, 20,
                            DataObjects.OBJECT_ALPHA, 7),
                    List.of(
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(
                    "         ",
                    "  αα αα  ",
                    "  βββββ  ",
                    "  βββββ  ",
                    "   α α   ",
                    "  βββββ  ",
                    "  βββββ  ",
                    "    α    ",
                    "         ",
                    "         "
                    //"* ╱ ╲ ■ □ ᥩ α β γ "
            ), List.of(
                    "          ",
                    "          ",
                    "  ╲╱╲╱╲  ",
                    "         ",
                    "          ",
                    "  ╲╱╲╱╲  ",
                    "         ",
                    "          "))),

            new PointData(27L, 8L, 500L, 1200L, 1800L,

                    Map.of(DataObjects.OBJECT_BOX, 12),
                    List.of(
                            "  □□□□",
                            " □□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□ ",
                            "□□□□  "), null, List.of(List.of(
                    "      ",
                    " ■■■■ ",
                    " ■  ■ ",
                    " ■  ■ ",
                    " ■■■■ ",
                    "  "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(28L, 18L, 500L, 1800L, 2200L,

                    Map.of(DataObjects.OBJECT_BOX, 10),
                    List.of(
                            "  □□□□□ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "  □□□□□  "),
                    null, List.of(List.of(
                    "        ",
                    "        ",
                    "■■     ■■",
                    "■       ■",
                    "■■     ■■",
                    "        "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(29L, 15L, 500L, 2500L, 5500L,

                    Map.of(DataObjects.OBJECT_BOX, 20),
                    List.of(
                            "□□ □□ □□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            " □□□□□□",
                            " □□□□□□"),
                    null, List.of(List.of(
                    "□□ □□ □□",
                    "■■■■■■■■",
                    "■□□□□□□■",
                    "■■□□□□■■",
                    " ■■■■■■ ",
                    " □□□□□□ "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(30L, 10L, 100L, 200L, 400L,

                    Map.of(DataObjects.OBJECT_BARREL, 2),//WIZARD

                    List.of(
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□"
                    ),
                    null, List.of(List.of(
                    "PYRBGRBPY",
                    "GRBPYBRGP",
                    "RPGYPYBRP",
                    "PGPBPYPYG",
                    "GRPPYGBRP",
                    "RPGRBPYPP",
                    "BGPBPYGBR"), List.of(
                    "        ",
                    "  ᥩ  ᥩ  ",
                    "        ",
                    "        ",
                    "        ",
                    "        "
                    //"* ╱ ╲ ■ □ ᥩ α β γ ",
            ))),

            new PointData(31L, 35L, 500L, 1500L, 5000L,

                    Map.of(DataObjects.OBJECT_YELLOW, 30,
                            DataObjects.OBJECT_PURPLE, 30,
                            DataObjects.OBJECT_BETA, 30),
                    List.of(
                            "□□□   □□□",
                            "□□□   □□□",
                            "□□□   □□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(
                    "ββ     ββ",
                    "         ",
                    "β       β",
                    "ββββ ββββ",
                    "ββββ ββββ",
                    "ββββ ββββ",
                    "■■■■■■■■■  "
            ), List.of(
                    "        ",
                    "        ",
                    "        ",
                    "╱╲    ╱╲",
                    "",
                    "",
                    ""))),
            new PointData(32L, 30L, 500L, 2500L, 5500L,

                    Map.of(DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            "□□□□□□□□□",
                            "□□ □□□□□□",
                            "□□□□□□□□□",
                            "□□ □□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"
                    ), null, List.of(List.of(
                    "ᥩᥩ■■□□□□□",
                    "□□ ■□□□□□",
                    "□□■■□□□□□",
                    "□□■■□□□□□",
                    "■■■■□□□□□",
                    "□□■■□□□□□",
                    "□□■■□□□□□"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(33L, 35L, 500L, 8500L, 9000L,

                    Map.of(DataObjects.OBJECT_BETA, 9,
                            DataObjects.OBJECT_GOLD, 15),
                    List.of(
                            "        □",
                            "       □□",
                            "      □□□",
                            "     □□□□",
                            "    □□□□□",
                            "   □□□□□□",
                            "  □□□□□□□",
                            " □□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(
                    "          ",
                    "          ",
                    "          ",
                    "          ",
                    "    ββββ■",
                    "    βββ■■",
                    "    ββ■■■",
                    "    β■■■■",
                    "    ■■■■■"
                    //"* ╱ ╲ ■ □ ᥩ α β γ "
            ), List.of(
                    "          ",
                    "          ",
                    "          ",
                    "          ",
                    "        $",
                    "       $$",
                    "      $$$",
                    "     $$$$",
                    "    $$$$$"))),


            new PointData(34L, (28L - 12L), 500L, 2500L, 2700L,

                    Map.of(DataObjects.OBJECT_BETA, 10,
                            DataObjects.OBJECT_ALPHA, 4),
                    List.of(
                            " □□□□□□□ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□ □□□□",
                            " □□□□□□□ "),
                    null, List.of(List.of(
                    "  βββββ  ",
                    "         ",
                    "  αα αα  ",
                    "         ",
                    "         ",
                    "  βββββ  "
                    //"* ╱ ╲ ■ □ ᥩ α β γ ",
            ))),

            new PointData(35L, 30L, 1000L, 1500L, 2700L,

                    Map.of(DataObjects.OBJECT_BARREL, 4,
                            DataObjects.OBJECT_ALPHA, 6),
                    List.of(
                            " □□□□□□□ ",
                            "□□□□ □□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            " □□□□□□□ "),
                    //" □□□□□□□ ",
                    //"□□□□□□□□□"),
                    null, List.of(List.of(
                    " ᥩ ᥩ ᥩ ᥩ ",
                    "α       α",
                    "         ",
                    "α       α",
                    "         ",
                    "α       α"
                    //"* ╱ ╲ ■ □ ᥩ α β γ ",
            ))),


            new PointData(36L, 24L - 8L, 500L, 1500L, 2500L,
                    Map.of(DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            "  □   □  ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "  □□□□□  "),
                    null, List.of(List.of(
                    "  ᥩ   ᥩ  ",
                    "         ",
                    "■■■■■■■■■",
                    "         ",
                    "         ",
                    "         ",
                    "         ",
                    "         "
                    //"* ╱ ╲ ■ □ ᥩ α β γ ",
            ))),
            new PointData(37L, 21L, 500L, 1000L, 1500L,

                    Map.of(DataObjects.OBJECT_ALPHA, 2,
                            DataObjects.OBJECT_BARREL, 4),
                    List.of(
                            "  □   □  ",
                            "  □□□□□  ",
                            " □□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(
                    "  ᥩ   ᥩ  ",
                    "  ᥩ   ᥩ  ",
                    "  α   α  ",
                    "         ",
                    "         ",
                    "         ",
                    "         "
                    //"* ╱ ╲ ■ □ ᥩ α β γ ",

            ))),

            new PointData(38L, 40L, 500L, 2500L, 5500L,

                    Map.of(DataObjects.OBJECT_BOX, 34),
                    List.of(
                            " □□□□□□□ ",
                            "□□□□□□□□□",
                            "□□□□ □□□□",
                            "□□□   □□□",
                            "□□□□ □□□□",
                            "□□□□□□□□□",
                            " □□□□□□□ "),
                    null, List.of(List.of(
                    " ■■■■■■■ ",
                    "■□□□■□□□■",
                    "■□□■ ■□□■",
                    "■■■   ■■■",
                    "■□□■ ■□□■",
                    "■□□□■□□□■",
                    " ■■■■■■■ "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(39L, 30L, 500L, 2500L, 5500L,

                    Map.of(DataObjects.OBJECT_BARREL, 1),
                    List.of(
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    //"□□□□□□□□□"),
                    null, List.of(List.of(
                    "□□□ᥩ□□□□□",
                    "■□□□□□□□□",
                    "■■□□□□□□□",
                    "■■■□□□□□□",
                    "■■■■□□□□□",
                    "■■■■■  ",
                    "■■■■■■ ",
                    "■■■■■■ ",
                    "■■■■■■ ",
                    "■■■■■■ "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))), //List.of(
            // "□□□□□□□□□",
            //"X□□□□□□□□",
            //"XX□□□□□□□",
            //"XXX□□□□□□",
            //"XXXX□□□□□",
            //"XXXXX    ",
            //"XXXXXX   ",
            //"XXXXXX   ",
            //"XXXXXX   "))),

            new PointData(40L, 25L, 500L, 2500L, 3000L,

                    Map.of(DataObjects.OBJECT_BETA, 18,
                            DataObjects.OBJECT_ALPHA, 3),
                    List.of(
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"
                    ), null, List.of(List.of(
                    "         ",
                    "βββββββββ",
                    "   ααα   ",
                    "βββββββββ",
                    "         "
                    //"* ╱ ╲ ■ □ ᥩ α β γ "
            ), List.of(
                    "          ",
                    "  ╲╱╲╱╲   ",
                    "           ",
                    "  ╱╲╱╲╱ ",
                    "          "
            ))),

            new PointData(41L, 20L, 500L, 2500L, 2200L,

                    Map.of(DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            "",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"),
                    null, List.of(List.of(            //WIZARD BLOCKS
                    "RGBYPBGBP",
                    "GBYPBGBPY",
                    "YPYGRGRGB",
                    "GGYPGPBPY",
                    "RGBBPRGBP",
                    "GBYBRGBPY",
                    "RGBYBRGBP",
                    "GRGBYBRGB"), List.of(
                    "        ",
                    "        ",
                    "        ",
                    "  ᥩ   ᥩ  ",
                    "         ",
                    "         ",
                    "         ",
                    "  ‖   ‖  ",
                    "        "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(42L, 25L, 500L, 2500L, 3300L,

                    Map.of(DataObjects.OBJECT_BARREL, 3),
                    List.of(
                            " □  □  □ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□")
           /* gems: [
             "RGBYPRGBY",
             "GRBBYPRGB",
             "BRGBYPRGB",
             "YGBGBYPRG",
             "PRRYYBYPB",
             "RGBGBYPRG",
             "GPYRGBYPR",
             "BBRRGBYPR",
             ],*/
                    , null, List.of(List.of(
                    " ᥩ  ᥩ  ᥩ ",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□ ‖□□ ‖□",
                    "□□□□□□□□□",
                    "       "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ), List.of(
                    "         ",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□ *□□□* ",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "       "))),

            new PointData(43L, 25L, 500L, 2500L, 2980L,

                    Map.of(DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            "  □    □  ",
                            " □□□□□□□□",
                            //  "□□□□□□□□",
                            " □□□□□□□□",
                            " □□□□□□□□",
                            " □□□□□□□□",
                            " □□□□□□□□",
                            " □□□□□□□□",
                            "□□□□□□□□□□  "
                    ), null, List.of(List.of(
                    "  ᥩ    ᥩ",
                    "",
                    //"□□□□□□□□",
                    "",
                    " ‖□‖  ‖□‖",
                    "",
                    "",
                    ""
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ), List.of("        ",
                    "",
                    //     "□□□□□□□□",
                    "",
                    "",
                    " * *  * *",
                    "",
                    "",
                    ""
            ))),

            new PointData(44L, 30L, 500L, 2500L, 2400L,

                    Map.of(DataObjects.OBJECT_BARREL, 5),
                    List.of(
                            "  □□□□□  ",
                            "  □□□□□ ",
                            "□□□ □ □□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□"
                    ), null, List.of(List.of(
                    "  ᥩᥩᥩᥩᥩ  ",
                    "  □□□□□ ",
                    "□□□ □ □□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□",
                    "□□□□□□□□□"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(45L, 15L, 500L, 2500L, 1000L,

                    Map.of(DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            " □□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□□□□□□"), null, List.of(List.of(
                    " ᥩ□□□□ᥩ",
                    "‖      ‖",
                    "*      *",
                    "‖      ‖",
                    "*      *",
                    "‖      ‖"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(46L, 15L, 500L, 2500L, 200L,

                    Map.of(DataObjects.OBJECT_SAND, 6),
                    List.of(
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□"), List.of(
                    "RYGSGR",
                    "YRSBYP",
                    "RGRSBY",
                    "GRRBSR",
                    "YPYSBY",
                    "PRSYRG"), null),

            new PointData(47L, 40L, 600L, 2500L, 3300L,

                    Map.of(DataObjects.OBJECT_SAND, 9,
                            DataObjects.OBJECT_GREEN, 25),
                    List.of(
                            " □□□ □□□ ",
                            " □□□ □□□ ",
                            " □□□□□□□ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "  □□□□□  "),
                    List.of(
                            "?PRSPSPS?",
                            "?SBGGRRP?",
                            "?BPSBGSG?",
                            "YPRBRSPPY",
                            "YPRGSYPSG",
                            "YPGBRBPPY",
                            //"PRBSPRGB",
                            //"RBSBSGBP",
                            "??GBRRG??"), null),

            new PointData(48L, 20L, 600L, 2500L, 1000L,

                    Map.of(DataObjects.OBJECT_RED, 18,
                            DataObjects.OBJECT_BLUE, 18),
                    List.of(
                            "  □   □  ",
                            " □□□ □□□ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            " □□□□□□□ "),
                    null, List.of(List.of(
                    "        ",
                    "        ",
                    "        ",
                    "        ",
                    "        ",
                    "        "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(49L, 30L, 600L, 1000L, 1200L,

                    Map.of(DataObjects.OBJECT_PURPLE, 20,
                            DataObjects.OBJECT_YELLOW, 20),//whites

                    List.of(
                            " □□□□□□ ",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            "□□□  □□□",
                            "□□□  □□□",
                            "□□□□□□□□",
                            "□□□□□□□□",
                            " □□□□□□"), List.of(
                    "????????",
                    "S?S?S?S?",
                    "????????",
                    "S?S?S?S?",
                    "????????",
                    "S?S?S?S?",
                    "????????",
                    "S?S?S?S?"), null),

            new PointData(50L, 30L, 600L, 2500L, 4100L,

                    Map.of(DataObjects.OBJECT_SAND, 8,
                            DataObjects.OBJECT_BETA, 12,
                            DataObjects.OBJECT_BOX, 12),
                    List.of(
                            "   □□□□",
                            "  □□□□□",
                            " □□□□□□",
                            "□□□□□□□",
                            "□□□□□□ ",
                            "□□□□□  ",
                            "□□□□   "
                    ), null, List.of(List.of(
                    "   □□□□",
                    "  ■■■■□",
                    " □■□□■□",
                    "□□■□□■□",
                    "□□■■■■ ",
                    "□□□□□  ",
                    "□□□□   "
            ), List.of(
                    "   □□□□",
                    "  □□□□□",
                    " ββββ□□",
                    "□β□□β□□",
                    "□β□□β□ ",
                    "□ββββ  ",
                    "□□□□   "
            ), List.of(
                    "   S□S□",
                    "  □□S□□",
                    " □□SS□□",
                    "S□SSS□",
                    "S□SS□□ ",
                    "□S□□  ",
                    "S□S□  "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),
            new PointData(51L, 10L, 600L, 2500L, 500L,

                    Map.of(DataObjects.OBJECT_GAMMA, 4),
                    List.of(
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□",
                            "□□□□□□"), List.of(             //WIZARD GREEN-SPIDER-2
                    "RGBRYP",
                    "PYRBGY",
                    "PYPPGP",
                    "YGYYRP",
                    "PPYRPG",
                    "RGBGRP"
            ), List.of(List.of(
                    "□□□□□□",
                    "□□□□□□",
                    "□□γγ□□",
                    "□□γγ□□",
                    "□□□□□□",
                    "□□□□□□"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(52L, 40L, 600L, 2500L, 3100L,

                    Map.of(DataObjects.OBJECT_GAMMA, 14,
                            DataObjects.OBJECT_BARREL, 2),
                    List.of(
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            " □□□□□□□",
                            " □□□□□□□",
                            " □□   □□",
                            " □□□□□□□",
                            " □□□□□□□"
                    ), null, List.of(List.of(
                    " □□ᥩ□ᥩ□□",
                    " □□‖□‖□□",
                    " γγ*γ*γγ",
                    " γ□   □γ",
                    " γγγγγγγ",
                    " □□□□□□□"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(53L, 40L, 500L, 2500L, 5000L,

                    Map.of(DataObjects.OBJECT_GAMMA, 25,
                            DataObjects.OBJECT_BARREL, 4),
                    List.of(
                            " □ □ □ □ ",
                            " □ □ □ □ ",
                            " □ □ □ □ ",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "  □□□□□  "
                    ), null, List.of(List.of(
                    " ᥩ ᥩ ᥩ ᥩ ",
                    " □ □ □ □ ",
                    " □ □ □ □ ",
                    "□γγ□γ□γγ□",
                    "□γγ□γ□γγ□",
                    "□γγ□γ□γγ□",
                    "□γγ□γ□γγ□",
                    "  γγγγγ  "
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))),

            new PointData(54L, 25L, 500L, 2500L, 3000L,

                    Map.of(DataObjects.OBJECT_GAMMA, 20,
                            DataObjects.OBJECT_PURPLE, 20),
                    List.of(
                            "□□ □□□ □□",
                            "□□ □□□ □□",
                            "□□□□□□□□□",
                            "□□□□□□□□□",
                            "   □□□   ",
                            "□□□□□□□□□"
                    ), null, List.of(List.of(
                    "□γ γγγ γ□",
                    "□γ γγγ γ□",
                    "□γ□γγγ□γ□",
                    "□γ□γγγ□γ□",
                    "   □□□   ",
                    "□□□□□□□□□"
                    /** α β γ ■ ╱ ╲ Ξ ᥩ ‖ */
            ))));


    @Override
    public List<PointData> getPointsByMapId(Long mapId) {

        Long firstPointId = MapSettings.getFirstPointId(mapId);
        Long lastPointId = MapSettings.getLastPointId(mapId);

        List<PointData> points = new ArrayList<>();
        for (Long id = firstPointId; id <= lastPointId; id++) {
            points.add(getById(id));
        }
        return points;
    }

    public PointData getById(Long id) {
        return points.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point not found"));
    }
}
