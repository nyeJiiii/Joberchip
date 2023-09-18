package kr.joberchip.server.v1.space.page.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class SpacePageResponse {

  private String title;

  private String description;

  private Set<SpacePageResponse> childPages = new LinkedHashSet<>();

  //  private List<TextBlockDTO> textBlocks;
  //
  //  private List<LinkBlockDTO> linkBlocks;
  //
  //  private List<ImageBlockDTO> imageBlocks;
  //
  //  private List<VideoBlockDTO> videoBlocks;
  //
  //  private Set<HashtagDTO> hashtags = new LinkedHashSet<>();

  private SpacePageProfileDTO spacePageProfile;
}
