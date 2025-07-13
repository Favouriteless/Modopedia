package net.favouriteless.modopedia.api.text;

import net.minecraft.resources.ResourceLocation;

public class FormattedStringBuilder {

    private final StringBuilder builder;

    private FormattedStringBuilder() {
        this.builder = new StringBuilder();
    }

    public static FormattedStringBuilder begin() {
        return new FormattedStringBuilder();
    }

    public FormattedStringBuilder then(String text) {
        builder.append(text);
        return this;
    }

    public FormattedStringBuilder paragraph() {
        return then("\n\n");
    }

    public FormattedStringBuilder paragraph(String text) {
        return paragraph().then(text);
    }

    public FormattedStringBuilder linebreak() {
        return then("\n");
    }

    public FormattedStringBuilder linebreak(String text) {
        return linebreak().then(text);
    }

    public FormattedStringBuilder apply(String tag) {
        return then("$(").then(tag).then(")");
    }

    public FormattedStringBuilder apply(String tag, String argument) {
        return apply(tag + ":" + argument);
    }

    public FormattedStringBuilder bold() {
        return apply("b");
    }

    public FormattedStringBuilder bold(String text) {
        return bold().then(text).stopBold();
    }

    public FormattedStringBuilder stopBold() {
        return apply("/b");
    }

    public FormattedStringBuilder italic() {
        return apply("i");
    }

    public FormattedStringBuilder italic(String text) {
        return italic().then(text).stopItalic();
    }

    public FormattedStringBuilder stopItalic() {
        return apply("/i");
    }

    public FormattedStringBuilder underline() {
        return apply("u");
    }

    public FormattedStringBuilder underline(String text) {
        return underline().then(text).stopUnderline();
    }

    public FormattedStringBuilder stopUnderline() {
        return apply("/u");
    }

    public FormattedStringBuilder strikethrough() {
        return apply("s");
    }

    public FormattedStringBuilder strikethrough(String text) {
        return strikethrough().then(text).stopStrikethrough();
    }

    public FormattedStringBuilder stopStrikethrough() {
        return apply("/s");
    }

    public FormattedStringBuilder obfuscated() {
        return apply("o");
    }

    public FormattedStringBuilder obfuscated(String text) {
        return obfuscated().then(text).stopObfuscated();
    }

    public FormattedStringBuilder stopObfuscated() {
        return apply("/o");
    }

    public FormattedStringBuilder url(String url, String text) {
        return apply("l", url).then(text).apply("/l");
    }

    public FormattedStringBuilder url(String url, String text, int colour) {
        return colour(colour).apply("l", url).then(text).apply("/l").stopColour();
    }

    public FormattedStringBuilder command(String command, String text) {
        return apply("cmd", command).then(text).apply("/cmd");
    }

    public FormattedStringBuilder clipboard(String copy, String text) {
        return apply("clip", copy).then(text).apply("/clip");
    }

    public FormattedStringBuilder tooltip(String tooltip, String text) {
        return apply("t", tooltip).then(text).apply("/t");
    }

    public FormattedStringBuilder colour(int colour) {
        return apply("c", "#" + Integer.toHexString(colour).toUpperCase());
    }

    public FormattedStringBuilder colour(int colour, String text) {
        return colour(colour).then(text).stopColour();
    }

    public FormattedStringBuilder stopColour() {
        return apply("/c");
    }

    public FormattedStringBuilder clear() {
        return apply("");
    }

    public FormattedStringBuilder font(ResourceLocation font) {
        return apply("f:" + font);
    }

    public FormattedStringBuilder stopFont() {
        return apply("/f");
    }

    public FormattedStringBuilder entryLink(String entry, String text) {
        return apply("el:" + entry).then(text).apply("/el");
    }

    public FormattedStringBuilder entryLink(String entry, String text, int colour) {
        return colour(colour).apply("el:" + entry).then(text).apply("/el").stopColour();
    }

    public FormattedStringBuilder categoryLink(String category, String text) {
        return apply("cl:" + category).then(text).apply("/cl");
    }

    public FormattedStringBuilder categoryLink(String category, String text, int colour) {
        return colour(colour).apply("cl:" + category).then(text).apply("/cl").stopColour();
    }

    public FormattedStringBuilder boldEntryLink(String entry, String text) {
        return bold().entryLink(entry, text).stopBold();
    }

    public FormattedStringBuilder boldEntryLink(String entry, String text, int colour) {
        return colour(colour).bold().entryLink(entry, text).stopBold().stopColour();
    }

    public FormattedStringBuilder boldCategoryLink(String entry, String text) {
        return bold().categoryLink(entry, text).stopBold();
    }

    public FormattedStringBuilder boldCategoryLink(String entry, String text, int colour) {
        return colour(colour).bold().categoryLink(entry, text).stopBold().stopColour();
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
