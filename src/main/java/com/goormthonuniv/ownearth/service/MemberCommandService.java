package com.goormthonuniv.ownearth.service;

import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.*;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.*;

public interface MemberCommandService {
  Member signUpMember(SignUpMemberRequest request);
}
