package com.example.mymarket.presentation.screens.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mymarket.R
import com.example.mymarket.domain.model.Product
import com.example.mymarket.presentation.components.CustomButton
import com.example.mymarket.util.StringExt.formatPriceWithZero


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductListRow(
    modifier: Modifier = Modifier,
    product: Product,
    onItemClick: (Product) -> Unit? = {},
    onFavoriteClick: (Product) -> Unit? = {}
) {
    val isFavorite = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
        .clickable { onItemClick(product) }
        .shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Box(modifier = Modifier) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    painter = rememberImagePainter(data = product.image),
                    contentScale = ContentScale.Fit,
                    contentDescription = product.description,
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp)
                        .clickable {
                            onFavoriteClick(product)
                            isFavorite.value = !isFavorite.value
                        }
                        .padding(4.dp),
                    painter = if (isFavorite.value == true) painterResource(id = R.drawable.ic_favorites_yellow) else painterResource(
                        id = R.drawable.ic_favorites
                    ),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price?.formatPriceWithZero() ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = " ₺")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.brand ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.model ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                title = stringResource(id = R.string.add_to_card)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewItem() {
    ProductListRow(
        product = Product(
            createdAt = "2023-07-17T07:21:02.529Z",
            name = "Bentley Focus",
            image = "https://loremflickr.com/640/480/food",
            price = "514.00",
            description = "Quasi adipisci sint veniam delectus. Illum laboriosam minima dignissimos natus earum facere consequuntur eius vero. Itaque facilis at tempore ipsa. Accusamus nihil fugit velit possimus expedita error porro aliquid. Optio magni mollitia veritatis repudiandae tenetur nemo. Id consectetur fuga ipsam quidem voluptatibus sed magni dolore.\nFacilis commodi dolores sapiente delectus nihil ex a perferendis. Totam deserunt assumenda inventore. Incidunt nesciunt adipisci natus porro deleniti nisi incidunt laudantium soluta. Nostrum optio ab facilis quisquam.\nSoluta laudantium ipsa ut accusantium possimus rem. Illo voluptatibus culpa incidunt repudiandae placeat animi. Delectus id in animi incidunt autem. Ipsum provident beatae nisi cumque nulla iure.",
            model = "CTS",
            brand = "Lamborghini",
            id = "1"
        ),
        onItemClick = {}
    )

}