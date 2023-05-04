package cart.service;

import cart.dto.AuthInfoRequest;
import cart.repository.dao.CartDao;
import cart.repository.dao.MemberDao;
import cart.repository.dao.ProductDao;
import cart.repository.entity.CartEntity;
import cart.repository.entity.MemberEntity;
import cart.repository.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartService {

    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final CartDao cartDao;

    public CartService(final ProductDao productDao, final MemberDao memberDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.cartDao = cartDao;
    }

    public void addProductByAuthInfo(final Long productId, final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        cartDao.save(new CartEntity(memberEntity.getId(), productId));
    }

    public List<ProductEntity> findProductsByAuthInfo(final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        return cartDao.findByMemberId(memberEntity.getId()).stream()
                .map(cartEntity -> productDao.findById(cartEntity.getProductId()))
                .collect(Collectors.toList());
    }

    public void deleteProductByAuthInfo(final Long productId, final AuthInfoRequest authInfoRequest) {
        final MemberEntity memberEntity = memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword());

        cartDao.delete(memberEntity.getId(), productId);
    }
}
