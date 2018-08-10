package bitcamp.ass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bitcamp.ass.dao.MemberDao;
import bitcamp.ass.domain.Member;

@Service
public class MemberService {

    @Autowired MemberDao memberDao;
    
    
}
