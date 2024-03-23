package com.goormthonuniv.ownearth.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goormthonuniv.ownearth.aws.s3.S3Client;
import com.goormthonuniv.ownearth.converter.MissionConverter;
import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MissionException;
import com.goormthonuniv.ownearth.feign.openai.dto.MissionImageAnalysisRequestDto;
import com.goormthonuniv.ownearth.feign.openai.dto.MissionImageAnalysisResponseDto;
import com.goormthonuniv.ownearth.feign.openai.service.OpenAiClient;
import com.goormthonuniv.ownearth.repository.MemberMissionRepository;
import com.goormthonuniv.ownearth.repository.MissionRepository;
import com.goormthonuniv.ownearth.service.MissionCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionCommandServiceImpl implements MissionCommandService {

  private final MissionRepository missionRepository;
  private final MemberMissionRepository memberMissionRepository;
  private final OpenAiClient openAiClient;
  private final S3Client s3Client;

  @Override
  public MemberMission getOrAssignMission(Member member) {

    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    return memberMissionRepository
        .findMemberMissionByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)
        .orElseGet(
            () -> {
              member.setIsMissionChangeable(true);

              return memberMissionRepository.save(
                  MissionConverter.toMemberMission(member, missionRepository.findRandomMission()));
            });
  }

  @Override
  public MemberMission accomplishMission(
      Member member, Long missionId, MultipartFile missionImage) {
    MemberMission memberMission =
        memberMissionRepository
            .findById(missionId)
            .orElseThrow(() -> new MissionException(GlobalErrorCode.MISSION_NOT_FOUND));

    if (memberMission.getIsCompleted()) {
      throw new MissionException(GlobalErrorCode.MISSION_ALREADY_ACCOMPLISHED);
    }

    String base64Image;
    try {
      base64Image =
          "data:"
              + missionImage.getContentType()
              + ";base64,"
              + Base64.getEncoder().encodeToString(missionImage.getBytes());
    } catch (IOException e) {
      throw new MissionException(GlobalErrorCode.MISSION_IMAGE_ANALYSIS_FAILED);
    }

    if (!isMissionSuccessful(memberMission.getMission(), base64Image)) {
      return memberMission;
    }

    String imageUrl = s3Client.uploadMissionImage(missionImage);
    memberMission.missionComplete(imageUrl);

    return memberMission;
  }

  private Boolean isMissionSuccessful(Mission mission, String image) {
    MissionImageAnalysisResponseDto response =
        openAiClient.requestImageAnalysis(
            MissionImageAnalysisRequestDto.from(mission.getContent(), image));

    return Boolean.parseBoolean(response.getAnswer());
  }

  public MemberMission changeMission(Member member) {

    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    MemberMission memberMission =
        memberMissionRepository
            .findMemberMissionByMemberAndCreatedAtBetween(member, startOfDay, endOfDay)
            .orElseThrow(() -> new MissionException(GlobalErrorCode.MISSION_NOT_FOUND));

    if (member.getIsMissionChangeable()) {
      member.setIsMissionChangeable(false);
      memberMission.setMission(missionRepository.findRandomMission());
    } else {
      member.decreasePoint();
      memberMission.setMission(missionRepository.findRandomMission());
    }

    return memberMission;
  }
}
