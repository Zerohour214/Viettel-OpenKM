package com.openkm.servlet.frontend;

import com.openkm.api.OKMAuth;
import com.openkm.api.VTOrg;
import com.openkm.dao.bean.Organization;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTOrganization;
import com.openkm.frontend.client.bean.GWTUser;
import com.openkm.frontend.client.constants.service.ErrorCode;
import com.openkm.frontend.client.service.VTOrgService;
import com.openkm.frontend.client.service.VTOrgServiceAsync;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.servlet.frontend.util.GWTUserComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VTOrgServlet extends OKMRemoteServiceServlet implements VTOrgService {
	private static Logger log = LoggerFactory.getLogger(VTOrgServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	public List<GWTOrganization> getAllOrg() throws OKMException {
		log.debug("getAllOrg()");
		List<GWTOrganization> orgList = new ArrayList<GWTOrganization>();
		updateSessionManager();

		try {
			for (Organization org : VTOrg.getInstance().getAllOrg()) {
				GWTOrganization gwtOrg = new GWTOrganization();
				gwtOrg.setId(org.getId());
				gwtOrg.setName(org.getName());
				orgList.add(gwtOrg);
			}

//			Collections.sort(orgList, GWTUserComparator.getInstance(getLanguage()));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OKMException(ErrorCode.get(ErrorCode.ORIGIN_OrgService, ErrorCode.CAUSE_General), e.getMessage());
		}

		log.debug("getAllOrg: {}", orgList);
		return orgList;
	}
}
