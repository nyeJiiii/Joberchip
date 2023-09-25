package kr.joberchip.server.v1.share.page.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import kr.joberchip.core.share.block.*;
import kr.joberchip.core.share.page.SharePage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BlockObjectResponse(
    UUID objectId,
    BlockType type,
    String title,
    String description,
    String src,
    Integer x,
    Integer y,
    Integer width,
    Integer height) {

  public static BlockObjectResponse fromEntity(SharePage sharePage) {
    return new BlockObjectResponse(
            sharePage.getPageId(),
            BlockType.PAGE,
            sharePage.getTitle(),
            sharePage.getDescription(),
            null,
            sharePage.getX(),
            sharePage.getY(),
            sharePage.getWidth(),
            sharePage.getHeight());
  }


  public static BlockObjectResponse fromEntity(TextBlock textBlock) {
    return new BlockObjectResponse(
        textBlock.getTextBlockId(),
        BlockType.TEXT,
        null,
        textBlock.getContent(),
        null,
        textBlock.getX(),
        textBlock.getY(),
        textBlock.getWidth(),
        textBlock.getHeight());
  }

  public static BlockObjectResponse fromEntity(LinkBlock linkBlock) {
    return new BlockObjectResponse(
        linkBlock.getLinkBlockId(),
        BlockType.LINK,
        linkBlock.getTitle(),
        linkBlock.getDescription(),
        linkBlock.getLink(),
        linkBlock.getX(),
        linkBlock.getY(),
        linkBlock.getWidth(),
        linkBlock.getHeight());
  }

  public static BlockObjectResponse fromEntity(TemplateBlock templateBlock) {
    return new BlockObjectResponse(
        templateBlock.getTemplateBlockId(),
        BlockType.TEMPLATE,
        templateBlock.getTitle(),
        templateBlock.getDescription(),
        null,
        templateBlock.getX(),
        templateBlock.getY(),
        templateBlock.getWidth(),
        templateBlock.getHeight());
  }

  public static BlockObjectResponse fromEntity(ImageBlock imageBlock) {
    return new BlockObjectResponse(
        imageBlock.getImageBlockId(),
        BlockType.IMAGE,
        imageBlock.getTitle(),
        imageBlock.getDescription(),
        imageBlock.getImageLink(),
        imageBlock.getX(),
        imageBlock.getY(),
        imageBlock.getWidth(),
        imageBlock.getHeight());
  }

  public static BlockObjectResponse fromEntity(VideoBlock videoBlock) {
    return new BlockObjectResponse(
        videoBlock.getVideoBlockId(),
        BlockType.VIDEO,
        videoBlock.getTitle(),
        videoBlock.getDescription(),
        videoBlock.getVideoLink(),
        videoBlock.getX(),
        videoBlock.getY(),
        videoBlock.getWidth(),
        videoBlock.getHeight());
  }
}
