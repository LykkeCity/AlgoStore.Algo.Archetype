#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.lykke.hft.ApiClient;
import com.lykke.hft.client.OrdersApi;
import com.lykke.hft.client.WalletsApi;
import com.lykke.hft.model.LimitOrderRequest;
import com.lykke.hft.model.MarketOrderRequest;
import com.lykke.hft.model.ResponseModelDouble;
import com.lykke.hft.model.Wallet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Algo {
	private Logger logger = LogManager.getLogger(getClass());

	String apiKey;

	OrdersApi ordersApi;
	WalletsApi walletsApi;
	String assetPairId;
	String asset;
	Double volume;
	Double margin;

	public Algo(OrdersApi ordersApi, WalletsApi walletsApi,String apiKey,String assetPairId, String asset, Double volume, Double margin) {

		this.ordersApi = ordersApi;
		this.walletsApi = walletsApi;
		this.apiKey = apiKey;
		this.assetPairId=assetPairId;
		this.asset=asset;
		this.volume=volume;
		this.margin=margin;

	}

	private void checkWallet(){
		List<Wallet> walletList = walletsApi.wallets(apiKey);
		logger.info(walletsApi.wallets(apiKey));

		for (Wallet wallet : walletList) {
			logger.info("Asset: " + wallet.getAssetId());
			logger.info("Ballance: " + wallet.getBalance());
			logger.info("GetReserved: "+wallet.getReserved());
			if (wallet.getBalance()==0){
				logger.info("Game over!!!");
				break;
			}
		}
	}

	private ResponseModelDouble placeMarketOrder(Double volume){

		MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
		marketOrderRequest.assetPairId(assetPairId);
		marketOrderRequest.setOrderAction(MarketOrderRequest.OrderActionEnum.BUY);
		marketOrderRequest.setVolume(volume);
		marketOrderRequest.setAsset(asset);
		return ordersApi.placeMarketOrder(apiKey, marketOrderRequest);

	}

	private UUID placeLimitOrder(Double price, Double volume){
		LimitOrderRequest limitOrderRequest = new LimitOrderRequest();
		limitOrderRequest.assetPairId(assetPairId);
		limitOrderRequest.setVolume(volume);
		limitOrderRequest.setPrice(price);
		limitOrderRequest.setOrderAction(LimitOrderRequest.OrderActionEnum.SELL);

		return ordersApi.placeLimitOrder(apiKey, limitOrderRequest);
	}

	public void run() throws InterruptedException, feign.FeignException{
		while(true) {

			logger.info("------------Check wallet!!!------------");
			checkWallet();

			logger.info("------------Buy some coins on the market price!------------");

			ResponseModelDouble responseModelDouble = placeMarketOrder(volume);

			Double boughtPrice = responseModelDouble.getResult();
			logger.info("------------Bought volume: " + volume + " on price: " + boughtPrice+"------------");


			Random rand = new Random();
			long seconds = rand.nextInt(10);

			logger.info("------------Sleeping for: " + seconds + " seconds!!!------------");
			Thread.sleep(seconds * 1000);

			Double sellPrice=boughtPrice+margin;
			logger.info("------------Selling "+volume + "coins with some profit on "+ sellPrice+"price!!!------------");
			UUID uuid = placeLimitOrder(sellPrice,volume);

			logger.info("------------What has happen with my order!!!------------");
			logger.info(ordersApi.getOrderInfo(uuid, apiKey));

			logger.info("------------Check wallet!!!------------");
			checkWallet();

		}
	}


	public static void main(String[] args) throws InterruptedException {
		Environment.load();
		String basePath = Environment.getVariable("HFT_API_BASE_PATH");
		String apiKey =   Environment.getVariable("HFT_KEY");
		String assetPair = Environment.getVariable("ASSET_PAIR");
		String asset = Environment.getVariable("ASSET");
		Double volume = Double.parseDouble(Environment.getVariable("VOLUME"));
		Double margin = Double.parseDouble(Environment.getVariable("MARGIN"));

		//TODO validate properties

		OrdersApi ordersApi = new ApiClient().setBasePath(basePath).buildClient(OrdersApi.class);
		WalletsApi walletsApi = new ApiClient().setBasePath(basePath).buildClient(WalletsApi.class);

		Algo algo  = new Algo(ordersApi,walletsApi,apiKey,assetPair,asset,volume,margin);
		try {
			algo.run();
		}catch (RuntimeException ex){
			System.out.println("Bad stuff happened!!! Bye!!!");

		}
	}

}