package m3.map.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataObjects {

    CELL_INVISIBLE(1, "field-none.png", "Не видна игроку."),
    CELL_VISIBLE(2, "field-cell.png", "???"),
    OBJECT_RANDOM(101, "field-none.png", "Случайнный камень из набора камней."),
    OBJECT_RED(102, "field-red.png", "Камень красный"),
    OBJECT_GREEN(103, "field-green.png", "Камень зеленый"),
    OBJECT_BLUE(104, "field-blue.png", "Камень голубой"),
    OBJECT_YELLOW(105, "field-yellow.png", "Камень желтый"),
    OBJECT_PURPLE(106, "field-purple.png", "Камень фиолетовый"),
    OBJECT_SAND(107, "field-sand.png", "Камень белый"),
    OBJECT_HOLE(120, "field-none.png", "Нет камня"),
    OBJECT_BARREL(130, "field-barrel.png", "Бочка"),
    OBJECT_BLOCK(135, "field-block.png", "Блок"),
    OBJECT_POLY_COLOR(140, "field-poly-color.png", "Многоцветный камень"),
    OBJECT_ALPHA(150, "field-alpha.png", "Монстр-1"),
    OBJECT_BETA(160, "field-beta.png", "Монстр-2"),
    OBJECT_GAMMA(161, "field-gamma.png", "Монстр-3"),
    OBJECT_TILE(170, "field-tile.png", "Плитка"),
    OBJECT_GOLD(180, "field-gold.png", "Драгоценности"),
    OBJECT_BOX(190, "field-box.png", "Ящик"),
    OBJECT_CHAIN_A(200, "field-chain-a.png", "Цепь"),
    OBJECT_CHAIN_B(210, "field-chain-b.png", "Цепь"),
    IS_EMITTER(1001, "field-none.png", "Эмитер камней"),
    WITH_LIGHTNING_HORIZONTAL(1010, "a-gem-light-1.png", "Молния хоризонтальная"),
    WITH_LIGHTNING_VERTICAL(1011, "a-gem-light-1.png", "Молния вертикальная"),
    WITH_LIGHTNING_CROSS(1012, "a-gem-light-1.png", "Молния кросс(двунаправленная)"),
    STUFF_HUMMER(2010, "button-hammer-rest.png", ""),
    STUFF_LIGHTNING(2011, "button-lightning-rest.png", ""),
    STUFF_SHUFFLE(2012, "button-shuffle-rest.png", ""),
    STUFF_GOLD(2013, "coing.png", ""),
    ;

    @JsonValue
    private final int id;
    private final String image;
    private final String comment;
}