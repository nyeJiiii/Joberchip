package kr.joberchip.server.v1.block.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import javax.persistence.Lob;
import kr.joberchip.core.block.*;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._utils.BlockType;

// TODO : Link, Video 링크 첨부하는 경우와 파일 첨부하는 경우 경로 다르게 설정되도록 해야함.
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BlockResponseDTO(
    UUID objectId,
    BlockType type,
    String title,
    String description,
    @Lob String src,
    Integer x,
    Integer y,
    Integer w,
    Integer h,
    Boolean visible) {

  public static BlockResponseDTO fromEntity(SharePage sharePage) {
    return new BlockResponseDTO(
        sharePage.getObjectId(),
        BlockType.PAGE,
        sharePage.getTitle(),
        sharePage.getDescription(),
        null,
        sharePage.getX(),
        sharePage.getY(),
        sharePage.getW(),
        sharePage.getH(),
        sharePage.getVisible());
  }

  public static BlockResponseDTO fromEntity(TextBlock textBlock) {
    return new BlockResponseDTO(
        textBlock.getObjectId(),
        BlockType.TEXT,
        null,
        null,
        textBlock.getContent(),
        textBlock.getX(),
        textBlock.getY(),
        textBlock.getW(),
        textBlock.getH(),
        textBlock.getVisible());
  }

  public static BlockResponseDTO fromEntity(LinkBlock linkBlock) {
    return new BlockResponseDTO(
        linkBlock.getObjectId(),
        BlockType.LINK,
        linkBlock.getTitle(),
        null,
        linkBlock.getLink(),
        linkBlock.getX(),
        linkBlock.getY(),
        linkBlock.getW(),
        linkBlock.getH(),
        linkBlock.getVisible());
  }

  public static BlockResponseDTO fromEntity(TemplateBlock templateBlock) {
    return new BlockResponseDTO(
        templateBlock.getObjectId(),
        BlockType.TEMPLATE,
        templateBlock.getTitle(),
        templateBlock.getDescription(),
        templateBlock.getLink(),
        templateBlock.getX(),
        templateBlock.getY(),
        templateBlock.getW(),
        templateBlock.getH(),
        templateBlock.getVisible());
  }

  public static BlockResponseDTO fromEntity(MapBlock mapBlock) {
    return new BlockResponseDTO(
        mapBlock.getObjectId(),
        BlockType.MAP,
        mapBlock.getAddress(),
        null,
        "{ \"latitude\" : "
            + mapBlock.getLatitude()
            + ", \"longitude\" : "
            + mapBlock.getLongitude()
            + "}",
        mapBlock.getX(),
        mapBlock.getY(),
        mapBlock.getW(),
        mapBlock.getH(),
        mapBlock.getVisible());
  }

  public static BlockResponseDTO fromEntity(ImageBlock imageBlock) {
    return new BlockResponseDTO(
        imageBlock.getObjectId(),
        BlockType.IMAGE,
        imageBlock.getTitle(),
        imageBlock.getDescription(),
        imageBlock.getImageLink(),
        imageBlock.getX(),
        imageBlock.getY(),
        imageBlock.getW(),
        imageBlock.getH(),
        imageBlock.getVisible());
  }

  public static BlockResponseDTO fromEntity(VideoBlock videoBlock) {
    return new BlockResponseDTO(
        videoBlock.getObjectId(),
        BlockType.VIDEO,
        videoBlock.getTitle(),
        videoBlock.getDescription(),
        videoBlock.getVideoLink(),
        videoBlock.getX(),
        videoBlock.getY(),
        videoBlock.getW(),
        videoBlock.getH(),
        videoBlock.getVisible());
  }
}
