package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String readProducts(final Model model) {
        final List<ProductDto> allProduct = productService.findAllProduct();
        model.addAttribute("products", allProduct);
        return "index";
    }
}
